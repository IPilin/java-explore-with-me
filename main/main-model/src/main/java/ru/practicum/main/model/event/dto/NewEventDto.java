package ru.practicum.main.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.location.LocationDto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 120)
    String title;
    @NotNull
    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;
    @NotNull
    @NotBlank
    @Size(min = 20, max = 7000)
    String description;
    LocalDateTime createdOn = LocalDateTime.now();
    @NotNull
    @Positive
    Long category;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NotNull
    LocationDto location;
    @NotNull
    Boolean paid;
    @NotNull
    @PositiveOrZero
    Integer participantLimit;
    @NotNull
    Boolean requestModeration;
}
