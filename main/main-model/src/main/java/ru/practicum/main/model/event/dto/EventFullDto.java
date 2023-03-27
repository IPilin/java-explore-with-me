package ru.practicum.main.model.event.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.category.CategoryDto;
import ru.practicum.main.model.event.model.EventState;
import ru.practicum.main.model.location.LocationDto;
import ru.practicum.main.model.user.UserShortDto;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto {
    Long id;
    String title;
    String annotation;
    String description;
    String createdOn;
    String eventDate;
    LocationDto location;
    CategoryDto category;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    UserShortDto initiator;
    EventState state;
    String publishedOn;
    Long confirmedRequests;
    Long views;
}
