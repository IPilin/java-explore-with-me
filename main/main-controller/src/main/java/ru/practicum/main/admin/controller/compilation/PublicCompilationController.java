package ru.practicum.main.admin.controller.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.compilation.CompilationListMapper;
import ru.practicum.main.model.compilation.CompilationMapper;
import ru.practicum.main.model.compilation.dto.CompilationDto;
import ru.practicum.main.service.compilation.CompilationService;

import java.util.Collection;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService service;
    private final CompilationMapper mapper;
    private final CompilationListMapper listMapper;

    @GetMapping
    public Collection<CompilationDto> findAllPublic(@RequestParam(required = false) Boolean pinned,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        return listMapper.toDto(service.findAllPublic(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public CompilationDto findPublic(@PathVariable Long compId) {
        return mapper.toDto(service.find(compId));
    }
}
