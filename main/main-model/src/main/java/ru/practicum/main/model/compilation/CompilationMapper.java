package ru.practicum.main.model.compilation;

import org.mapstruct.*;
import ru.practicum.main.model.compilation.dto.CompilationDto;
import ru.practicum.main.model.compilation.dto.NewCompilationDto;
import ru.practicum.main.model.compilation.model.Compilation;
import ru.practicum.main.model.event.EventMapper;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface CompilationMapper {
    CompilationDto toDto(Compilation compilation);

    @Mapping(target = "events", ignore = true)
    Compilation fromNewDto(NewCompilationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "events", ignore = true)
    void update(@MappingTarget Compilation compilation, NewCompilationDto dto);
}
