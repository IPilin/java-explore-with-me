package ru.practicum.main.model.event;

import org.mapstruct.*;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.event.dto.EventAdminDto;
import ru.practicum.main.model.event.dto.EventFullDto;
import ru.practicum.main.model.event.dto.EventShortDto;
import ru.practicum.main.model.event.dto.NewEventDto;
import ru.practicum.main.model.event.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event fromNewEvent(NewEventDto newEventDto);

    EventFullDto toFullDto(Event event);

    EventShortDto toShortDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Event event, NewEventDto updatedEventDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Event event, EventAdminDto updatedEventDto);

    default Category map(Long value) {
        var cat = new Category();
        cat.setId(value);
        return cat;
    }
}
