package ru.practicum.main.model.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiError {
    String status;
    String message;
    String reason;
    @Builder.Default
    final LocalDateTime timestamp = LocalDateTime.now();
}
