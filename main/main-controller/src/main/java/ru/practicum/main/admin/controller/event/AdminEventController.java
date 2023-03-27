package ru.practicum.main.admin.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.event.EventListMapper;
import ru.practicum.main.model.event.EventMapper;
import ru.practicum.main.model.event.dto.EventAdminDto;
import ru.practicum.main.model.event.dto.EventFullDto;
import ru.practicum.main.model.event.model.EventState;
import ru.practicum.main.service.event.EventService;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService service;
    private final EventMapper mapper;
    private final EventListMapper listMapper;

    @GetMapping
    public Collection<EventFullDto> getAllAdmin(@RequestParam(required = false) List<Long> users,
                                                @RequestParam(required = false) List<EventState> states,
                                                @RequestParam(required = false) List<Long> categories,
                                                @RequestParam(required = false) LocalDateTime rangeStart,
                                                @RequestParam(required = false) LocalDateTime rangeEnd,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return listMapper.toFullCollection(service.findAllAdmin(users, states, categories, rangeStart, rangeEnd, from, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateAdmin(@PathVariable @Positive Long eventId,
                                    @RequestBody EventAdminDto eventAdminDto) {
        return mapper.toFullDto(service.updateAdmin(eventId, eventAdminDto));
    }
}
