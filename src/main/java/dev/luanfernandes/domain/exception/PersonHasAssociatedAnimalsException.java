package dev.luanfernandes.domain.exception;

import org.springframework.http.HttpStatus;

public class PersonHasAssociatedAnimalsException extends BusinessException {
    public PersonHasAssociatedAnimalsException(String id) {
        super("Cannot delete person with ID: " + id + " because animals are associated with them.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
