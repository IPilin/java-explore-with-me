package ru.practicum.stat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stat.HitConverter;
import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.dto.StatsDto;
import ru.practicum.stat.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository repository;
    private final HitConverter converter;

    @Override
    public void create(HitDto hitDto) {
        repository.save(converter.fromDto(hitDto));
    }

    @Override
    public Collection<StatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            return uris == null || uris.isEmpty() ?
                    repository.findDistinctStats(start, end) :
                    repository.findDistinctStats(start, end, uris);
        }
        return uris == null || uris.isEmpty() ?
                repository.findStats(start, end) :
                repository.findStats(start, end, uris);
    }
}
