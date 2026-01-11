package com.im.echo.validation;

import com.im.echo.model.User;
import com.im.echo.websocket.ServerState;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.im.echo.validation.ValidationError.*;

public class UsernameValidator {

    private UsernameValidator() {}

    public static ValidationResult checkValidity(String username) {
        if (!contentExists(username))
            return ValidationResult.invalid(USERNAME_EMPTY_OR_NULL);

        String formattedUsername = format(username);

        if (!isAvailable(formattedUsername, ServerState.getUsers()))
            return ValidationResult.invalid(USERNAME_ALREADY_EXISTS);

        Set<ValidationError> errors = new HashSet<>();

        if (!isLongEnough(formattedUsername))
            errors.add(USERNAME_TOO_SHORT);
        if (!hasOnlyLetters(formattedUsername))
            errors.add(USERNAME_CONTAINS_INVALID_CHARACTERS);

        return ValidationResult.builder()
                .isValid(errors.isEmpty())
                .errors(errors)
                .build();
    }

    public static boolean contentExists(String username) {
        return username != null && !username.isEmpty();
    }

    public static boolean isLongEnough(String username) {
        return username.length() >= 3;
    }

    public static boolean hasOnlyLetters(String username) {
        return username.matches("^[a-zA-Z]*$");
    }

    public static boolean isAvailable(String username, Collection<User> users) {
        return !users.contains(User.builder().name(UsernameValidator.format(username)).build());
    }

    public static String format(String username) {
        return username.toLowerCase().replaceAll("\\s+","");
    }

}
