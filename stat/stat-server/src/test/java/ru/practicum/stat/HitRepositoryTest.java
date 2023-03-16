package ru.practicum.stat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.repository.HitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class HitRepositoryTest {
    @Autowired
    HitRepository repository;
    HitConverter converter = new HitConverter();

    @Test
    public void saveHitTest() {
        var hitDto = new HitDto(null, "app", "uri", "1.1.1.1", "2022-11-12 11:23:55");
        var hit = repository.save(converter.fromDto(hitDto));

        assertThat(hit.getApp()).isEqualTo(hitDto.getApp());
        assertThat(hit.getUri()).isEqualTo(hitDto.getUri());
        assertThat(hit.getIp()).isEqualTo(hitDto.getIp());
        assertThat(hit.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).isEqualTo(hitDto.getTimestamp());
    }

    @Test
    public void findStatsTest() {
        var hitDto = new HitDto(null, "app", "uri", "1.1.1.1", "2022-11-12 11:23:55");
        repository.save(converter.fromDto(hitDto));
        repository.save(converter.fromDto(hitDto));
        repository.save(converter.fromDto(hitDto));
        hitDto.setUri("newUri");
        repository.save(converter.fromDto(hitDto));

        var stats = repository.findStats(LocalDateTime.now().minusYears(2), LocalDateTime.now());

        assertThat(stats.size()).isEqualTo(2);
        assertThat(stats.get(0).getApp()).isEqualTo(hitDto.getApp());
        assertThat(stats.get(0).getUri()).isEqualTo("uri");
        assertThat(stats.get(0).getHits()).isEqualTo(3L);

        assertThat(stats.get(1).getApp()).isEqualTo(hitDto.getApp());
        assertThat(stats.get(1).getUri()).isEqualTo(hitDto.getUri());
        assertThat(stats.get(1).getHits()).isEqualTo(1L);

        stats = repository.findStats(LocalDateTime.now().minusYears(2), LocalDateTime.now(), List.of("uri"));
        assertThat(stats.size()).isEqualTo(1);
        assertThat(stats.get(0).getApp()).isEqualTo(hitDto.getApp());
        assertThat(stats.get(0).getUri()).isEqualTo("uri");
        assertThat(stats.get(0).getHits()).isEqualTo(3L);

        stats = repository.findDistinctStats(LocalDateTime.now().minusYears(2), LocalDateTime.now());
        assertThat(stats.size()).isEqualTo(2);
        assertThat(stats.get(0).getApp()).isEqualTo(hitDto.getApp());
        assertThat(stats.get(0).getHits()).isEqualTo(1L);

        assertThat(stats.get(1).getApp()).isEqualTo(hitDto.getApp());
        assertThat(stats.get(1).getHits()).isEqualTo(1L);

        stats = repository.findDistinctStats(LocalDateTime.now().minusYears(2), LocalDateTime.now(), List.of("uri"));
        assertThat(stats.size()).isEqualTo(1);
        assertThat(stats.get(0).getApp()).isEqualTo(hitDto.getApp());
        assertThat(stats.get(0).getHits()).isEqualTo(1L);
    }
}
