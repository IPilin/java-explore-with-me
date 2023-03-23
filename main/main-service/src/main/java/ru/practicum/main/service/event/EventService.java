package ru.practicum.main.service.event;

import ru.practicum.main.model.event.dto.NewEventDto;
import ru.practicum.main.model.event.model.Event;

import java.util.Collection;

public interface EventService {
    Collection<Event> getAllByUserId(Long userId, Integer from, Integer size);

    Event create(Long userId, NewEventDto eventDto);

    Event get(Long userId, Long eventId);

    Event update(Long userId, Long eventId, NewEventDto eventDto);
}
