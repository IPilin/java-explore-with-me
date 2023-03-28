package ru.practicum.main.model.request;

import org.mapstruct.Mapper;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.request.dto.RequestDto;
import ru.practicum.main.model.request.model.Request;
import ru.practicum.main.model.user.User;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    RequestDto toDto(Request request);

    default Long map(Event event) {
        return event.getId();
    }

    default Long map(User user) {
        return user.getId();
    }
}
