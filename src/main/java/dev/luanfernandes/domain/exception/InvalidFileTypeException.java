package dev.luanfernandes.domain.exception;

public class InvalidFileTypeException extends BusinessException {
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
