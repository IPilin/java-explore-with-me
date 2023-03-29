package ru.practicum.stat;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {
    @Mapping(source = "timestamp", target = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Hit fromDto(HitDto hitDto);
}
