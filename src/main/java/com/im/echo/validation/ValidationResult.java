package com.im.echo.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class ValidationResult {
    private boolean isValid;
    private Set<ValidationError> errors;

    public static ValidationResult invalid(ValidationError... errors) {
        if (errors == null || errors.length == 0) {
            throw new IllegalArgumentException("Errors cannot be null or empty");
        }

        return ValidationResult.builder()
                .isValid(false)
                .errors(new HashSet<>(Arrays.asList(errors)))
                .build();
    }
}
