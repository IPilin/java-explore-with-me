package ru.practicum.main.model.comment.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.comment.model.CommentStatus;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentShortDto {
    Long id;
    String content;
    String author;
    LocalDateTime created;
    Long event;
    Integer comments;
    Long parentId;
    CommentStatus status;
}
