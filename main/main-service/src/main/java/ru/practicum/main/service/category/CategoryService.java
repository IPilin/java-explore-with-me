package ru.practicum.main.service.category;

import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.category.CategoryDto;

import java.util.Collection;

public interface CategoryService {
    Category create(CategoryDto categoryDto);

    Category change(Long catId, CategoryDto categoryDto);

    void remove(Long catId);

    Collection<Category> getAll(Integer from, Integer size);

    Category getById(Long catId);
}
