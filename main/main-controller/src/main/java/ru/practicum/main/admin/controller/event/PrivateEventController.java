package ru.practicum.main.admin.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.converter.Converter;
import ru.practicum.main.model.event.dto.EventFullDto;
import ru.practicum.main.model.event.dto.EventShortDto;
import ru.practicum.main.model.event.dto.NewEventDto;
import ru.practicum.main.service.event.EventService;

import javax.validation.constraints.Positive;
import java.util.Collection;

@Validated
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService service;
    private final Converter converter;

    @GetMapping
    public Collection<EventShortDto> getAllByUserId(@PathVariable @Positive Long userId) {
        return converter.toClassCollection(service.getAllByUserId(userId), EventShortDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable @Positive Long userId,
                               @RequestBody @Validated NewEventDto eventDto) {
        return converter.toClass(service.create(userId, eventDto), EventFullDto.class);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getById(@PathVariable @Positive Long userId,
                         @PathVariable @Positive Long eventId) {
        return converter.toClass(service.get(userId, eventId), EventFullDto.class);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable @Positive Long userId,
                        @PathVariable @Positive Long eventId,
                        @RequestBody @Validated NewEventDto eventDto) {
        return converter.toClass(service.update(userId, eventId, eventDto), EventFullDto.class);
    }
}
