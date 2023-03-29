package ru.practicum.main.model.compilation;

import org.mapstruct.Mapper;
import ru.practicum.main.model.compilation.dto.CompilationDto;
import ru.practicum.main.model.compilation.model.Compilation;

import java.util.Collection;

@Mapper(componentModel = "spring", uses = CompilationMapper.class)
public interface CompilationListMapper {
    Collection<CompilationDto> toDto(Collection<Compilation> compilations);
}
