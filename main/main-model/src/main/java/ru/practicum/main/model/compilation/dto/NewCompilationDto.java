package ru.practicum.main.model.compilation.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.validation.OnCreate;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {
    @NotNull(groups = OnCreate.class)
    String title;
    Boolean pinned = false;
    @NotNull(groups = OnCreate.class)
    List<Long> events;
}
