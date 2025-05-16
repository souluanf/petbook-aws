package dev.luanfernandes.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BrazilianZipcodeValidator implements ConstraintValidator<ValidBrazilianZipcode, String> {

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if (cep == null) return false;
        return cep.matches("^\\d{8}$");
    }
}
