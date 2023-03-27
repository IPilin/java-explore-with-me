package ru.practicum.main.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.model.compilation.CompilationMapper;
import ru.practicum.main.model.compilation.dto.NewCompilationDto;
import ru.practicum.main.model.compilation.model.Compilation;
import ru.practicum.main.model.exception.ConflictException;
import ru.practicum.main.model.exception.NotFoundException;
import ru.practicum.main.repository.compilation.CompilationRepository;
import ru.practicum.main.repository.event.EventRepository;

import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;
    private final EventRepository eventRepository;
    private final CompilationMapper mapper;

    @Override
    public Compilation create(NewCompilationDto compilationDto) {
        var compilation = mapper.fromNewDto(compilationDto);
        compilation.setEvents(eventRepository.findAllByIdIn(compilationDto.getEvents()));
        try {
            return repository.save(compilation);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Compilation title must be unique.");
        }
    }

    @Override
    public void delete(Long compId) {
        try {
            repository.deleteById(compId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Compilation with id=%d not found.", compId));
        }
    }

    @Override
    public Compilation update(Long compId, NewCompilationDto compilationDto) {
        var compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation with id=%d not found.", compId)));
        mapper.update(compilation, compilationDto);
        if (Objects.nonNull(compilationDto.getEvents())) {
            compilation.setEvents(eventRepository.findAllByIdIn(compilationDto.getEvents()));
        }

        return repository.save(compilation);
    }

    @Override
    public Compilation find(Long compId) {
        return repository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Compilation with id=%d not found.", compId)));
    }

    @Override
    public Collection<Compilation> findAllPublic(Boolean pinned, Integer from, Integer size) {
        return pinned == null ?
                repository.findAll(PageRequest.of(from / size, size)).getContent() :
                repository.findAllByPinnedIs(pinned, PageRequest.of(from / size, size));
    }
}
