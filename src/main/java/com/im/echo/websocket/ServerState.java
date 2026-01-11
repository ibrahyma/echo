package com.im.echo.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.im.echo.exceptions.BadUsernameException;
import com.im.echo.model.Message;
import com.im.echo.model.User;
import com.im.echo.util.JsonMapper;
import com.im.echo.validation.UsernameValidator;
import com.im.echo.validation.ValidationResult;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ServerState {
    @Getter
    private static final ArrayList<User> users = new ArrayList<>();

    @Getter
    private static final ArrayList<Message> messages = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(ServerState.class);

    public static User addUser(String name) throws BadUsernameException {
        ValidationResult validationResult = UsernameValidator.checkValidity(name);

        if (!validationResult.isValid())
            throw new BadUsernameException(validationResult);

        User addedUser = User.builder().name(UsernameValidator.format(name)).build();
        users.add(addedUser);
        return addedUser;
    }

    public static boolean removeUser(User user) {
        return users.remove(user);
    }

    public static Message addMessage(Message message) throws JsonProcessingException {
        if (messages.stream().anyMatch(m -> m.getId().equals(message.getId()))) return null;
        logger.info(JsonMapper.parse(message));
        messages.add(message);
        return message;
    }
}
