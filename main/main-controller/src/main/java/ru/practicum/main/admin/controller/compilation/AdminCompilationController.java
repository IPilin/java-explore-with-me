package ru.practicum.main.admin.controller.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.compilation.CompilationMapper;
import ru.practicum.main.model.compilation.dto.CompilationDto;
import ru.practicum.main.model.compilation.dto.NewCompilationDto;
import ru.practicum.main.model.validation.OnCreate;
import ru.practicum.main.service.compilation.CompilationService;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService service;
    private final CompilationMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@RequestBody @Validated(OnCreate.class) NewCompilationDto compilationDto) {
        return mapper.toDto(service.create(compilationDto));
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long compId) {
        service.delete(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto update(@PathVariable Long compId,
                                 @RequestBody NewCompilationDto compilationDto) {
        return mapper.toDto(service.update(compId, compilationDto));
    }
}
