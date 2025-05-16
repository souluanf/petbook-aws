package dev.luanfernandes.domain.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends BusinessException {
    public AlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
