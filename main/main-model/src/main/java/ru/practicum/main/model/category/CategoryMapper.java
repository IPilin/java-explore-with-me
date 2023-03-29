package ru.practicum.main.model.category;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category fromDto(CategoryDto categoryDto);
}
