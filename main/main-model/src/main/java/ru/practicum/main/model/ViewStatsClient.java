package ru.practicum.main.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.practicum.main.model.constant.AppConstants;
import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.dto.StatsDto;
import ru.practicum.stat.web.StatsClient;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ViewStatsClient {
    private final static LocalDateTime START_TIME = LocalDateTime.of(2023, 1, 1, 1, 1, 1);
    private final static LocalDateTime END_TIME = LocalDateTime.of(2123, 1, 1, 1, 1, 1);
    private final String appName;
    private final StatsClient statsClient;

    public ViewStatsClient(@Value("${stats-server-url}") String url,
                           @Value("${app-name}") String appName) {
        statsClient = new StatsClient(url);
        this.appName = appName;
    }

    public void saveStats(Long eventId, String ip) {
        var hit = HitDto.builder()
                .app(appName)
                .uri("/events/" + eventId)
                .timestamp(LocalDateTime.now().format(AppConstants.FORMATTER))
                .ip(ip)
                .build();
        statsClient.createHit(hit);
    }

    public void saveStats(String endpoint, String ip) {
        var hit = HitDto.builder()
                .app(appName)
                .uri(endpoint)
                .timestamp(LocalDateTime.now().format(AppConstants.FORMATTER))
                .ip(ip)
                .build();
        statsClient.createHit(hit);
    }

    public Collection<StatsDto> getStats(List<Long> eventsIds) {
        var uris = eventsIds.stream().map((id) -> "/events/" + id).collect(Collectors.toList());
        return statsClient.getStats(START_TIME, END_TIME, uris, false);
    }
}
