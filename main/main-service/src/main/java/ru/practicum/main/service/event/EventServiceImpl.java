package ru.practicum.main.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.main.model.constant.AppConstants;
import ru.practicum.main.model.event.converter.EventConverter;
import ru.practicum.main.model.event.dto.EventState;
import ru.practicum.main.model.event.dto.NewEventDto;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.exception.ForbiddenException;
import ru.practicum.main.repository.event.EventRepository;
import ru.practicum.main.repository.event.LocationRepository;
import ru.practicum.main.service.category.CategoryService;
import ru.practicum.main.service.user.UserService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
    private final LocationRepository locationRepository;
    private final EventConverter converter;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public Collection<Event> getAllByUserId(Long userId) {
        return null;
    }

    @Override
    public Event create(Long userId, NewEventDto eventDto) {
        validateEventTime(eventDto);
        var event = converter.toClass(eventDto, Event.class);
        event.setState(EventState.PENDING);
        event.setInitiator(userService.get(userId));
        event.setCategory(categoryService.getById(eventDto.getCategory()));
        event.setLocation(locationRepository.save(event.getLocation()));
        return repository.save(event);
    }

    @Override
    public Event get(Long userId, Long eventId) {
        return null;
    }

    @Override
    public Event update(Long userId, Long eventId, NewEventDto eventDto) {
        return null;
    }

    private void validateEventTime(NewEventDto eventDto) {
        if (eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ForbiddenException(
                    String.format(
                            "Field: eventDate. Error: должно содержать дату, которая еще не наступила. Value: %s",
                            eventDto.getEventDate().format(AppConstants.FORMATTER)));
        }
    }
}
