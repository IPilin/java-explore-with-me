package ru.practicum.main.admin.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.event.EventListMapper;
import ru.practicum.main.model.event.EventMapper;
import ru.practicum.main.model.event.dto.EventFullDto;
import ru.practicum.main.model.event.dto.EventShortDto;
import ru.practicum.main.model.event.dto.NewEventDto;
import ru.practicum.main.model.request.RequestListMapper;
import ru.practicum.main.model.request.dto.RequestDto;
import ru.practicum.main.model.request.dto.UpdateRequestDto;
import ru.practicum.main.model.validation.OnCreate;
import ru.practicum.main.model.validation.OnUpdate;
import ru.practicum.main.service.event.EventService;
import ru.practicum.main.service.request.RequestService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService service;
    private final RequestService requestService;
    private final EventMapper mapper;
    private final EventListMapper listMapper;
    private final RequestListMapper requestListMapper;

    @GetMapping
    public Collection<EventShortDto> getAllByUserId(@PathVariable @Positive Long userId,
                                                    @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                    @RequestParam(defaultValue = "10") @Positive Integer size) {
        return listMapper.toShortCollection(service.getAllByUserId(userId, from, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable @Positive Long userId,
                               @RequestBody @Validated(OnCreate.class) NewEventDto eventDto) {
        return mapper.toFullDto(service.create(userId, eventDto));
    }

    @GetMapping("/{eventId}")
    public EventFullDto getById(@PathVariable @Positive Long userId,
                         @PathVariable @Positive Long eventId) {
        return mapper.toFullDto(service.find(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable @Positive Long userId,
                        @PathVariable @Positive Long eventId,
                        @RequestBody @Validated(OnUpdate.class) NewEventDto eventDto) {
        return mapper.toFullDto(service.update(userId, eventId, eventDto));
    }

    @GetMapping("/{eventId}/requests")
    public Collection<RequestDto> getRequests(@PathVariable @Positive Long userId,
                                              @PathVariable @Positive Long eventId) {
        return requestListMapper.toDto(requestService.getAllInEvent(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests")
    public Map<String, Collection<RequestDto>> updateRequests(@PathVariable @Positive Long userId,
                                                        @PathVariable @Positive Long eventId,
                                                        @RequestBody @Validated UpdateRequestDto updateRequestDto) {
        return requestService.updateRequests(userId, eventId, updateRequestDto);
    }
}
