import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HitDto {
    @EqualsAndHashCode.Include
    Long id;
    String app;
    String uri;
    String ip;
    String timestamp;
}
