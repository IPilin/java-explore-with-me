package ru.practicum.main.admin.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.event.EventMapper;
import ru.practicum.main.model.event.dto.EventFullDto;
import ru.practicum.main.model.event.dto.EventShortDto;
import ru.practicum.main.model.event.dto.NewEventDto;
import ru.practicum.main.service.event.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@Validated
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService service;
    private final EventMapper mapper;

    @GetMapping
    public Collection<EventShortDto> getAllByUserId(@PathVariable @Positive Long userId,
                                                    @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                    @RequestParam(defaultValue = "10") @Positive Integer size) {
        //return mapper.convertCollection(service.getAllByUserId(userId, from, size), EventShortDto.class);
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable @Positive Long userId,
                               @RequestBody @Validated NewEventDto eventDto) {
        return mapper.toFullDto(service.create(userId, eventDto));
    }

    @GetMapping("/{eventId}")
    public EventFullDto getById(@PathVariable @Positive Long userId,
                         @PathVariable @Positive Long eventId) {
        return mapper.toFullDto(service.get(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable @Positive Long userId,
                        @PathVariable @Positive Long eventId,
                        @RequestBody @Validated NewEventDto eventDto) {
        return mapper.toFullDto(service.update(userId, eventId, eventDto));
    }
}
