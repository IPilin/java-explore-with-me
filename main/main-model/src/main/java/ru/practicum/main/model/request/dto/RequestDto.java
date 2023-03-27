package ru.practicum.main.model.request.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.request.model.RequestState;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {
    Long id;
    String created;
    Long event;
    Long requester;
    RequestState status;
}
