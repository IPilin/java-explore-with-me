package ru.practicum.stat.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
