package ru.practicum.main.admin.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.event.EventListMapper;
import ru.practicum.main.model.event.EventMapper;
import ru.practicum.main.model.event.EventSort;
import ru.practicum.main.model.event.dto.EventFullDto;
import ru.practicum.main.model.event.dto.EventShortDto;
import ru.practicum.main.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService service;
    private final EventMapper mapper;
    private final EventListMapper listMapper;

    @GetMapping
    public Collection<EventShortDto> getAll(@RequestParam(required = false) String text,
                                            @RequestParam(required = false) List<Long> categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false) LocalDateTime rangeStart,
                                            @RequestParam(required = false) LocalDateTime rangeEnd,
                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(required = false) EventSort sort,
                                            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                            @RequestParam(defaultValue = "10") @Positive Integer size,
                                            HttpServletRequest httpRequest) {
        return listMapper.toShortCollection(
                service.findAllPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, httpRequest.getRemoteAddr()));
    }

    @GetMapping("/{eventId}")
    public EventFullDto getPublic(@PathVariable @Positive Long eventId,
                                  HttpServletRequest httpRequest) {
        return mapper.toFullDto(service.findPublic(eventId, httpRequest.getRemoteAddr()));
    }
}