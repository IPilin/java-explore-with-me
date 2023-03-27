package ru.practicum.main.model.event.filter;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.event.EventSort;
import ru.practicum.main.model.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFilter {
    String text;
    List<Long> users;
    List<EventState> states;
    List<Long> categories;
    Boolean paid;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    EventSort sort;
}
