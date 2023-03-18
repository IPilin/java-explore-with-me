package ru.practicum.stat.service;

import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface HitService {
    void create(HitDto hitDto);

    Collection<StatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
