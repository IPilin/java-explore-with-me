package ru.practicum.main.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.event.model.EventStateAction;
import ru.practicum.main.model.location.LocationDto;
import ru.practicum.main.model.validation.OnCreate;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {
    @NotNull(groups = OnCreate.class)
    @NotBlank(groups = OnCreate.class)
    @Size(min = 3, max = 120, groups = OnCreate.class)
    String title;
    @NotNull(groups = OnCreate.class)
    @NotBlank(groups = OnCreate.class)
    @Size(min = 20, max = 2000, groups = OnCreate.class)
    String annotation;
    @NotNull(groups = OnCreate.class)
    @NotBlank(groups = OnCreate.class)
    @Size(min = 20, max = 7000, groups = OnCreate.class)
    String description;
    @NotNull(groups = OnCreate.class)
    @Positive(groups = OnCreate.class)
    Long category;
    LocalDateTime createdOn = LocalDateTime.now();
    @NotNull(groups = OnCreate.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NotNull(groups = OnCreate.class)
    LocationDto location;
    @NotNull(groups = OnCreate.class)
    Boolean paid;
    @NotNull(groups = OnCreate.class)
    @PositiveOrZero(groups = OnCreate.class)
    Integer participantLimit;
    @NotNull(groups = OnCreate.class)
    Boolean requestModeration;
    @Null(groups = OnCreate.class)
    EventStateAction stateAction;
}
