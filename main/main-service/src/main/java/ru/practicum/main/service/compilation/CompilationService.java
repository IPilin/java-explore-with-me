package ru.practicum.main.service.compilation;

import ru.practicum.main.model.compilation.dto.NewCompilationDto;
import ru.practicum.main.model.compilation.model.Compilation;

import java.util.Collection;

public interface CompilationService {
    Compilation create(NewCompilationDto compilationDto);

    void delete(Long compId);

    Compilation update(Long compId, NewCompilationDto compilationDto);

    Compilation find(Long compId);

    Collection<Compilation> findAllPublic(Boolean pinned, Integer from, Integer size);
}
