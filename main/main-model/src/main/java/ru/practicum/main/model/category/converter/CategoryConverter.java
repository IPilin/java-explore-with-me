package ru.practicum.main.model.category.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.category.CategoryDto;

@Component
public class CategoryConverter {
    private final ModelMapper mapper;

    public CategoryConverter() {
        mapper = new ModelMapper();
    }

    public Category fromDto(CategoryDto categoryDto) {
        return mapper.map(categoryDto, Category.class);
    }
}
