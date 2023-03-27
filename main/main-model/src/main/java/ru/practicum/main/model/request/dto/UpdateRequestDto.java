package ru.practicum.main.model.request.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.request.model.RequestState;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRequestDto {
    @NotNull
    List<Long> requestIds;
    @NotNull
    RequestState status;
}
