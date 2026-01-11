package com.im.echo.exceptions;

import com.im.echo.validation.ValidationError;
import com.im.echo.validation.ValidationResult;

import java.io.IOException;
import java.util.Set;

public class BadUsernameException extends IOException {

    public BadUsernameException(ValidationResult validationResult) {
        super(getErrorMessage(validationResult.getErrors()));
    }

    private static String getErrorMessage(Set<ValidationError> errors) {
        return String.format(
                "Username %s.",
                errors.stream().map(ValidationError::getMessage).reduce((s1, s2) -> s1 + ", " + s2).orElse(".")
        );
    }
}
