package ru.practicum.main.model.event.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.category.CategoryDto;
import ru.practicum.main.model.user.UserShortDto;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {
    Long id;
    String title;
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;
    String eventDate;
    UserShortDto initiator;
    Boolean paid;
    Integer views;
}
