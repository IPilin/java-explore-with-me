package ru.practicum.main.model.event;

import org.mapstruct.Mapper;
import ru.practicum.main.model.event.dto.EventFullDto;
import ru.practicum.main.model.event.dto.EventShortDto;
import ru.practicum.main.model.event.model.Event;

import java.util.Collection;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface EventListMapper {
    Collection<EventShortDto> toShortCollection(Collection<Event> events);
    Collection<EventFullDto> toFullCollection(Collection<Event> events);
}
