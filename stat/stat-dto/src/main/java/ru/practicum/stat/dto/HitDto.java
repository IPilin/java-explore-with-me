package ru.practicum.stat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
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
