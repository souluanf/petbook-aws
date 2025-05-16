package dev.luanfernandes.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = BrazilianZipcodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBrazilianZipcode {
    String message() default "Must be valid and follow the Brazilian length (8 digits with no special characters)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
