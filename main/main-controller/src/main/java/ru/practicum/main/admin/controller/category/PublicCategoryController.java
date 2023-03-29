package ru.practicum.main.admin.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.service.category.CategoryService;

import javax.validation.constraints.Positive;
import java.util.Collection;

@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryService service;

    @GetMapping
    public Collection<Category> getAll(@RequestParam(defaultValue = "0") Integer from,
                                       @RequestParam(defaultValue = "10") @Positive Integer size) {
        return service.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public Category getById(@PathVariable @Positive Long catId) {
        return service.getById(catId);
    }
}
