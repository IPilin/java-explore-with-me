package ru.practicum.main.model.comment.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.validation.OnCreate;
import ru.practicum.main.model.validation.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCommentDto {
    @NotNull(groups = OnCreate.class)
    @NotBlank(groups = OnCreate.class)
    String content;

    @Null(groups = OnUpdate.class)
    Long parentId;
}
