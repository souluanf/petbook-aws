package dev.luanfernandes.domain.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return BAD_REQUEST;
    }
}
