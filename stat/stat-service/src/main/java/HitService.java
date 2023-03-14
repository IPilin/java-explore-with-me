import dto.HitDto;
import dto.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface HitService {
    void create(HitDto hitDto);
    Collection<StatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
