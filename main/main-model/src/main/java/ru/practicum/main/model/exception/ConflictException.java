package ru.practicum.main.model.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(final String message) {
        super(message);
    }
}
