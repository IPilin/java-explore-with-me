import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.dto.StatsDto;
import ru.practicum.stat.web.StatsClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatsClientTest {
    private MockWebServer server;
    private StatsClient client;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() throws IOException {
        mapper = new ObjectMapper();
        server = new MockWebServer();
        server.start();
        client = new StatsClient(String.format("http://localhost:%s", server.getPort()));
    }

    @Test
    public void webClientHitTest() throws JsonProcessingException, InterruptedException {
        server.enqueue(new MockResponse());
        var dto = new HitDto(1L, "app", "uri", "1.1.1.1", "2022-09-06 11:00:23");
        client.createHit(dto);
        //client.getStats(LocalDateTime.now(), LocalDateTime.now(), List.of("uri"), false);
        var request = server.takeRequest();
        assertEquals(request.getMethod(), "POST");
        assertEquals(request.getPath(), "/hit");

        var requestDto = mapper.readValue(request.getBody().readUtf8(), HitDto.class);
        assertEquals(dto.getId(), requestDto.getId());
        assertEquals(dto.getApp(), requestDto.getApp());
        assertEquals(dto.getUri(), requestDto.getUri());
        assertEquals(dto.getIp(), requestDto.getIp());
        assertEquals(dto.getTimestamp(), requestDto.getTimestamp());
    }

    @Test
    public void webClientStatsTest() throws JsonProcessingException, InterruptedException {
        var stats = new StatsDto("app", "uri", 2L);
        var response = new MockResponse()
                .setResponseCode(200)
                .setBody(mapper.writeValueAsString(List.of(stats)))
                .setHeader("Content-Type", "application/json");
        server.enqueue(response);

        var time = LocalDateTime.of(2022, 1, 12, 11, 29, 55);
        var answer = client.getStats(time, time.plusYears(1), List.of(""), false);

        var request = server.takeRequest();
        assertEquals(request.getMethod(), "GET");
        assertEquals(request.getPath(),"/stats?start=2022-01-12%2011:29:55&end=2023-01-12%2011:29:55&uris=&unique=false");

        assertEquals(answer, List.of(stats));
    }

    @AfterEach
    public void shutDown() throws IOException {
        server.shutdown();
    }
}
