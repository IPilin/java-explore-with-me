package ru.practicum.main.model.request;

import org.mapstruct.Mapper;
import ru.practicum.main.model.request.dto.RequestDto;
import ru.practicum.main.model.request.model.Request;

import java.util.Collection;

@Mapper(componentModel = "spring", uses = RequestMapper.class)
public interface RequestListMapper {
    Collection<RequestDto> toDto(Collection<Request> requests);
}
