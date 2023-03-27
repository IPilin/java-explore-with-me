package ru.practicum.stat.web;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.dto.StatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StatsClient {
    private final WebClient client;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatsClient(String url) {
        client = WebClient.create(url);
    }

    public void createHit(HitDto hitDto) {
        client.post()
                .uri(uriBuilder -> uriBuilder.path("/hit").build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(hitDto), HitDto.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        var response = client.get()
                .uri(uriBuilder -> uriBuilder.path("/stats")
                        .queryParam("start", start.format(format))
                        .queryParam("end", end.format(format))
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(StatsDto[].class)
                .block();
        return response == null ? new ArrayList<>() : List.of(response);
    }
}
