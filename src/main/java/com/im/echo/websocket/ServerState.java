package com.im.echo.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.im.echo.exceptions.UsernameAlreadyExistsException;
import com.im.echo.model.Message;
import com.im.echo.model.User;
import com.im.echo.util.JsonMapper;
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

    private static boolean usernameExists(String name) {
        return users.stream().anyMatch(user -> user.getName().equals(name.toLowerCase().trim()));
    }

    public static User addUser(String name) throws UsernameAlreadyExistsException {
        if (usernameExists(name)) {
            throw new UsernameAlreadyExistsException(String.format("Le nom %s est deja utilisé", name));
        }

        User addedUser = User.builder().name(name.toLowerCase()).build();
        users.add(addedUser);
        return addedUser;
    }

    public static boolean removeUser(User user) {
        if (user == null) return false;
        return users.removeIf(u -> user.getId().equals(u.getId()));
    }

    public static Message addMessage(Message message) throws JsonProcessingException {
        if (messages.stream().anyMatch(m -> m.getId().equals(message.getId()))) return null;
        logger.info(JsonMapper.parse(message));
        messages.add(message);
        return message;
    }
}
