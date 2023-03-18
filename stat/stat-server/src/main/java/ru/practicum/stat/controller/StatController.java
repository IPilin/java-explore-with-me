package ru.practicum.stat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.dto.StatsDto;
import ru.practicum.stat.service.HitService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatController {
    private final HitService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody HitDto hitDto) {
        service.create(hitDto);
    }

    @GetMapping("/stats")
    public Collection<StatsDto> getStats(@RequestParam LocalDateTime start,
                                         @RequestParam LocalDateTime end,
                                         @RequestParam(required = false) List<String> uris,
                                         @RequestParam(defaultValue = "false")Boolean unique) {
        return service.get(start, end, uris, unique);
    }
}
