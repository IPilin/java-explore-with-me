package ru.practicum.main.service.event;

import ru.practicum.main.model.event.EventSort;
import ru.practicum.main.model.event.dto.EventAdminDto;
import ru.practicum.main.model.event.model.EventState;
import ru.practicum.main.model.event.dto.NewEventDto;
import ru.practicum.main.model.event.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventService {
    Collection<Event> getAllByUserId(Long userId, Integer from, Integer size);

    Event create(Long userId, NewEventDto eventDto);

    Event find(Long userId, Long eventId);

    Event find(Long eventId);

    Event update(Long userId, Long eventId, NewEventDto eventDto);

    Event updateAdmin(Long eventId, EventAdminDto eventDto);

    Collection<Event> findAllAdmin(List<Long> users,
                                   List<EventState> states,
                                   List<Long> categories,
                                   LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd,
                                   Integer from, Integer size);

    Collection<Event> findAllPublic(String text,
                                    List<Long> categories,
                                    Boolean paid,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Boolean onlyAvailable,
                                    EventSort sort,
                                    Integer from, Integer size,
                                    String ip);
    Event findPublic(Long eventId, String ip);
}
