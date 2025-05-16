package dev.luanfernandes.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class BrazilianPhoneValidator implements ConstraintValidator<ValidBrazilianPhone, String> {

    private static final Set<String> VALID_DDDS = Set.of(
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "22", "24", "27", "28", "31", "32", "33", "34",
            "35", "37", "38", "41", "42", "43", "44", "45", "46", "51", "53", "54", "55", "61", "62", "63", "64", "65",
            "66", "67", "68", "69", "71", "73", "74", "75", "77", "79", "81", "82", "83", "84", "85", "86", "87", "88",
            "89", "91", "92", "93", "94", "95", "96", "97", "98", "99");

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || !phone.matches("^\\d{10,11}$")) {
            return false;
        }

        String ddd = phone.substring(0, 2);
        return VALID_DDDS.contains(ddd);
    }
}
