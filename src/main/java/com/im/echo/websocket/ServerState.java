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
    private final ArrayList<User> users;

    @Getter
    private final ArrayList<Message> messages;

    private final Logger logger = LoggerFactory.getLogger(ServerState.class);

    public ServerState() {
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    private boolean usernameExists(String name) {
        return this.users.stream().anyMatch(user -> user.getName().equals(name.toLowerCase().trim()));
    }

    public User addUser(String name) throws UsernameAlreadyExistsException {
        if (usernameExists(name)) {
            throw new UsernameAlreadyExistsException(String.format("Le nom %s est deja utilisé", name));
        }

        User addedUser = User.builder().name(name.toLowerCase()).build();
        this.users.add(addedUser);
        return addedUser;
    }

    public boolean removeUser(User user) {
        if (user == null) return false;
        return this.users.removeIf(u -> user.getId().equals(u.getId()));
    }

    public void addMessage(Message message) throws JsonProcessingException {
        this.logger.info(JsonMapper.parse(message));
        this.messages.add(message);
    }
}
