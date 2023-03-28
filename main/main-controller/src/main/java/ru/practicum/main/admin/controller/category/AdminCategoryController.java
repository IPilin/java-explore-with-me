package ru.practicum.main.admin.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.category.CategoryDto;
import ru.practicum.main.service.category.CategoryService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody @Validated CategoryDto categoryDto) {
        return service.create(categoryDto);
    }

    @PatchMapping("/{catId}")
    public Category change(@PathVariable @Positive Long catId,
                           @RequestBody @Validated CategoryDto categoryDto) {
        return service.change(catId, categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable @Positive Long catId) {
        service.remove(catId);
    }
}
