package ru.practicum.main.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.model.event.model.EventState;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.exception.ConflictException;
import ru.practicum.main.model.exception.ForbiddenException;
import ru.practicum.main.model.exception.NotFoundException;
import ru.practicum.main.model.request.RequestListMapper;
import ru.practicum.main.model.request.dto.RequestDto;
import ru.practicum.main.model.request.dto.UpdateRequestDto;
import ru.practicum.main.model.request.model.Request;
import ru.practicum.main.model.request.model.RequestState;
import ru.practicum.main.model.user.User;
import ru.practicum.main.repository.request.RequestRepository;
import ru.practicum.main.service.event.EventService;
import ru.practicum.main.service.user.UserService;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository repository;
    private final UserService userService;
    private final EventService eventService;
    private final RequestListMapper listMapper;

    @Transactional(readOnly = true)
    @Override
    public Collection<Request> getAll(Long userId) {
        return repository.findAllByRequesterIs(userService.get(userId));
    }

    @Transactional
    @Override
    public Request create(Long userId, Long eventId) {
        var user = userService.get(userId);
        var event = eventService.find(eventId);
        validateRequest(user, event);
        var request = Request.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now())
                .build();
        if (event.getRequestModeration()) {
            request.setStatus(RequestState.PENDING);
        } else {
            request.setStatus(RequestState.CONFIRMED);
        }
        return repository.save(request);
    }

    @Transactional
    @Override
    public Request cancel(Long userId, Long requestId) {
        var user = userService.get(userId);
        var request = repository.findByIdAndRequester(requestId, user)
                .orElseThrow(() -> new NotFoundException(String.format("Request with id=%d not found.", requestId)));
        request.setStatus(RequestState.CANCELED);
        return repository.save(request);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Request> getAllInEvent(Long userId, Long eventId) {
        var event = eventService.find(userId, eventId);
        return repository.findAllByEvent(event);
    }

    @Transactional
    @Override
    public Map<String, Collection<RequestDto>> updateRequests(Long userId, Long eventId, UpdateRequestDto updateRequestDto) {
        var event = eventService.find(userId, eventId);
        var requests = repository.findAllByEventAndIdIn(event, updateRequestDto.getRequestIds());
        if (requests.size() != updateRequestDto.getRequestIds().size()) {
            throw new NotFoundException("Some ids not found.");
        }

        var confirmed = new ArrayList<Request>();
        var rejected = new ArrayList<Request>();

        for (var request : requests) {
            if (event.getConfirmedRequests() < event.getParticipantLimit()) {
                if (request.getStatus() == RequestState.PENDING) {
                    request.setStatus(updateRequestDto.getStatus());
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                }
                switch (request.getStatus()) {
                    case CONFIRMED:
                        confirmed.add(request);
                        break;
                    case REJECTED:
                        rejected.add(request);
                        break;
                }
            } else {
                throw new ForbiddenException("Event participants is full.");
            }
        }

        repository.saveAll(requests);

        var result = new HashMap<String, Collection<RequestDto>>();
        result.put("confirmedRequests", listMapper.toDto(confirmed));
        result.put("rejectedRequests", listMapper.toDto(rejected));
        return result;
    }

    private void validateRequest(User user, Event event) {
        if (repository.existsByRequesterAndEvent(user, event)) {
            throw new ForbiddenException("Request already exists.");
        }
        if (Objects.equals(event.getParticipantLimit(), event.getConfirmedRequests())) {
            throw new ForbiddenException("Event participants is full.");
        }
        if (Objects.equals(user.getId(), event.getInitiator().getId())) {
            throw new ConflictException("User can't request his event.");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ForbiddenException("Event is not public.");
        }
    }
}
