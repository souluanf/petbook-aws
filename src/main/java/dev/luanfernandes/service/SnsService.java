package dev.luanfernandes.service;

import dev.luanfernandes.domain.entity.Person;
import dev.luanfernandes.domain.enums.PersonEventType;

public interface SnsService {
    void publishPersonEvent(PersonEventType type, Person person);
}
