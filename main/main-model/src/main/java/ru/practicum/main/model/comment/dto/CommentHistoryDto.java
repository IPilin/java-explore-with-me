package ru.practicum.main.model.comment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.main.model.comment.model.CommentHistory;

import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentHistoryDto {
    CommentShortDto comment;
    List<CommentHistory> history;
}
