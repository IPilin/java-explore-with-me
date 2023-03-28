package ru.practicum.main.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.model.ViewStatsClient;
import ru.practicum.main.model.constant.AppConstants;
import ru.practicum.main.model.event.EventMapper;
import ru.practicum.main.model.event.EventSort;
import ru.practicum.main.model.event.dto.EventAdminDto;
import ru.practicum.main.model.event.dto.NewEventDto;
import ru.practicum.main.model.event.filter.EventFilter;
import ru.practicum.main.model.event.filter.EventSpecification;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.event.model.EventState;
import ru.practicum.main.model.exception.ForbiddenException;
import ru.practicum.main.model.exception.NotFoundException;
import ru.practicum.main.model.request.model.Request;
import ru.practicum.main.model.request.model.RequestState;
import ru.practicum.main.repository.event.EventRepository;
import ru.practicum.main.repository.event.LocationRepository;
import ru.practicum.main.repository.request.RequestRepository;
import ru.practicum.main.service.category.CategoryService;
import ru.practicum.main.service.user.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final EventMapper mapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ViewStatsClient statsClient;

    @Override
    public Collection<Event> getAllByUserId(Long userId, Integer from, Integer size) {
        return load(repository.findAllByInitiator(userService.get(userId), PageRequest.of(from / size, size)));
    }

    @Override
    public Event create(Long userId, NewEventDto eventDto) {
        var event = mapper.fromNewEvent(eventDto);
        validateEventTime(event);
        event.setState(EventState.PENDING);
        event.setInitiator(userService.get(userId));
        event.setCategory(categoryService.getById(eventDto.getCategory()));
        event.setLocation(locationRepository.save(event.getLocation()));
        return repository.save(event);
    }

    @Override
    public Event find(Long userId, Long eventId) {
        var user = userService.get(userId);
        var event = repository.findByIdAndInitiatorIs(eventId, user)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));
        return load(event);
    }

    @Override
    public Event find(Long eventId) {
        var event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));
        return load(event);
    }

    @Override
    public Event update(Long userId, Long eventId, NewEventDto eventDto) {
        var event = find(userId, eventId);
        if (event.getState() == EventState.PUBLISHED) {
            throw new ForbiddenException("Only pending or canceled events can be changed");
        }
        mapper.update(event, eventDto);
        validateEventTime(event);
        if (eventDto.getStateAction() != null) {
            switch (eventDto.getStateAction()) {
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
            }
        }
        return repository.save(event);
    }

    @Override
    public Event updateAdmin(Long eventId, EventAdminDto eventDto) {
        var event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));
        mapper.update(event, eventDto);
        validateEventTime(event);
        if (event.getState() != EventState.PENDING) {
            throw new ForbiddenException(
                    String.format(
                            "Cannot publish the event because it's not in the right state: %s",
                            event.getState()));
        }
        switch (eventDto.getStateAction()) {
            case REJECT_EVENT:
                event.setState(EventState.CANCELED);
                break;
            case PUBLISH_EVENT:
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
                break;
        }
        return repository.save(event);
    }

    @Override
    public Collection<Event> findAllAdmin(List<Long> users,
                                          List<EventState> states,
                                          List<Long> categories,
                                          LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd,
                                          Integer from, Integer size) {
        var filter = EventFilter.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .build();
        var spec = new EventSpecification(filter);
        return load(repository.findAll(spec, PageRequest.of(from / size, size)).getContent());

    }

    @Override
    public Collection<Event> findAllPublic(String text,
                                           List<Long> categories,
                                           Boolean paid,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Boolean onlyAvailable,
                                           EventSort sort,
                                           Integer from, Integer size,
                                           String ip) {
        var filter = EventFilter.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .sort(sort)
                .build();
        var spec = new EventSpecification(filter);
        var events = load(repository.findAll(spec, PageRequest.of(from / size, size)).getContent());
        events.forEach((event) -> statsClient.saveStats(event.getId(), ip));
        statsClient.saveStats("/events", ip);
        return events;
    }

    @Override
    public Event findPublic(Long eventId, String ip) {
        var event = repository.findEventByIdAndStateEquals(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d not found", eventId)));
        load(event);
        statsClient.saveStats(eventId, ip);
        return event;
    }

    private void validateEventTime(Event eventDto) {
        if (eventDto.getEventDate() == null) {
            return;
        }
        if (eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ForbiddenException(
                    String.format(
                            "Field: eventDate. Error: должно содержать дату, которая еще не наступила. Value: %s",
                            eventDto.getEventDate().format(AppConstants.FORMATTER)));
        }
    }

    private Event load(Event event) {
        return load(List.of(event)).get(0);
    }

    private List<Event> load(List<Event> events) {
        loadViews(events);
        loadRequests(events);
        return events;
    }

    private void loadViews(List<Event> events) {
        var stats = statsClient.getStats(events.stream().map(Event::getId).collect(Collectors.toList()));
        for (var stat : stats) {
            if (stat.getUri().equals("/events")) {
                continue;
            }
            var id = Long.parseLong(stat.getUri().split("/")[2]);
            for (var event : events) {
                if (event.getId() == id) {
                    event.setViews(stat.getHits().intValue());
                }
            }
        }
    }

    private void loadRequests(List<Event> events) {
        var requests = requestRepository.findAllByEventInAndStatusIs(events, RequestState.CONFIRMED)
                .stream()
                .collect(Collectors.groupingBy(Request::getEvent, Collectors.counting()));
        events.forEach((event) -> event.setConfirmedRequests(requests.getOrDefault(event, 0L).intValue()));
    }
}
