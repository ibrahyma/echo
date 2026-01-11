package com.im.echo.validation;

import lombok.Getter;

import static com.im.echo.validation.ValidationType.USERNAME;

@Getter
public enum ValidationError {
    USERNAME_ALREADY_EXISTS(USERNAME, "is already taken"),
    USERNAME_TOO_SHORT(USERNAME, "must be at least 3 characters long"),
    USERNAME_CONTAINS_INVALID_CHARACTERS(USERNAME, "must contain only letters"),
    USERNAME_EMPTY_OR_NULL(USERNAME, "cannot be empty or null");

    private final ValidationType type;
    private final String message;

    ValidationError(ValidationType type, String message) {
        this.type = type;
        this.message = message;
    }
}
