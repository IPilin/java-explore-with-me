package ru.practicum.main.model.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.main.model.event.model.Event;
import ru.practicum.main.model.request.dto.RequestDto;
import ru.practicum.main.model.request.model.Request;
import ru.practicum.main.model.user.User;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    RequestDto toDto(Request request);

    default Long map(Event event) {
        return event.getId();
    }

    default Long map(User user) {
        return user.getId();
    }
}
