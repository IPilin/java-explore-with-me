package ru.practicum.stat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.stat.dto.StatsDto;
import ru.practicum.stat.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query("Select new ru.practicum.stat.dto.StatsDto(h.app, h.uri, count(h.ip)) " +
            "From Hit h " +
            "Where h.timestamp between ?1 and ?2 " +
            "Group by h.app, h.uri " +
            "Order by count(h.ip) desc")
    List<StatsDto> findStats(LocalDateTime start, LocalDateTime end);

    @Query("Select new ru.practicum.stat.dto.StatsDto(h.app, h.uri, count(h.ip)) " +
            "From Hit h " +
            "Where h.timestamp between ?1 and ?2 " +
            "And h.uri in(?3)" +
            "Group by h.app, h.uri " +
            "Order by count(h.ip) desc")
    List<StatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("Select new ru.practicum.stat.dto.StatsDto(h.app, h.uri, count(distinct h.ip)) " +
            "From Hit h " +
            "Where h.timestamp between ?1 and ?2 " +
            "Group by h.app, h.uri " +
            "Order by count(distinct h.ip) desc")
    List<StatsDto> findDistinctStats(LocalDateTime start, LocalDateTime end);

    @Query("Select new ru.practicum.stat.dto.StatsDto(h.app, h.uri, count(distinct h.ip)) " +
            "From Hit h " +
            "Where h.timestamp between ?1 and ?2 " +
            "And h.uri in(?3)" +
            "Group by h.app, h.uri " +
            "Order by count(distinct h.ip) desc")
    List<StatsDto> findDistinctStats(LocalDateTime start, LocalDateTime end, List<String> uris);
}
