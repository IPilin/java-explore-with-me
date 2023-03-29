package ru.practicum.main.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.category.CategoryDto;
import ru.practicum.main.model.category.CategoryMapper;
import ru.practicum.main.model.exception.ConflictException;
import ru.practicum.main.model.exception.NotFoundException;
import ru.practicum.main.repository.category.CategoryRepository;
import ru.practicum.main.repository.event.EventRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final EventRepository eventRepository;
    private final CategoryMapper mapper;

    @Override
    public Category create(CategoryDto categoryDto) {
        try {
            return repository.save(mapper.fromDto(categoryDto));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    @Override
    public Category change(Long catId, CategoryDto categoryDto) {
        if (!repository.existsById(catId)) {
            throw new NotFoundException(String.format("Category with id=%d was not found", catId));
        }
        categoryDto.setId(catId);
        return create(categoryDto);
    }

    @Override
    public void remove(Long catId) {
        try {
            if (eventRepository.existsByCategoryId(catId)) {
                throw new ConflictException("The category is not empty.");
            }
            repository.deleteById(catId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Category with id=%d was not found", catId));
        }
    }

    @Override
    public Collection<Category> getAll(Integer from, Integer size) {
        return repository.findAll(PageRequest.of(from / size, size)).getContent();
    }

    @Override
    public Category getById(Long catId) {
        return repository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", catId)));
    }
}
