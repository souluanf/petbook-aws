package dev.luanfernandes.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BrazilianPhoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBrazilianPhone {
    String message() default "Phone number must be valid and start with a valid Brazilian DDD (e.g., 11954875270)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
