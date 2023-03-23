package ru.practicum.stat;

import org.mapstruct.Mapper;
import ru.practicum.stat.dto.HitDto;
import ru.practicum.stat.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {
    Hit fromDto(HitDto hitDto);
}
