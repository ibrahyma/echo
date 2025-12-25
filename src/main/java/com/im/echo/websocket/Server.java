package com.im.echo.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.im.echo.exceptions.UsernameAlreadyExistsException;
import com.im.echo.model.Message;
import com.im.echo.model.User;
import com.im.echo.util.JsonMapper;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class Server extends WebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final int PORT = 4242;
    private static final ServerState serverState = new ServerState();

    public Server() {
        super(new InetSocketAddress(PORT));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        logger.debug("onOpen");
        String resourceDescriptor = clientHandshake.getResourceDescriptor();
        String name = resourceDescriptor.split("name=")[1].split("&")[0];

        try {
            User newUser = serverState.addUser(name);
            webSocket.setAttachment(newUser);
        }
        catch (UsernameAlreadyExistsException usernameExc) {
            logger.debug("Username already exists");
            Message errorMessage = Message.builder()
                    .content("Username already exists")
                    .error(true)
                    .build();

            try {
                logger.debug("Open connection error");
                webSocket.send(JsonMapper.parse(errorMessage));
            } catch (JsonProcessingException jsonExc) {
                logger.error("JSON parsing issued during open connection error transmission", jsonExc);
            }

            webSocket.close();
            return;
        }

        logger.info("The client {} is now connected", name);
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        logger.debug("onClose");
        User user = webSocket.getAttachment();
        boolean removed = serverState.removeUser(user);
        if (removed) logger.info("The client {} is now disconnected", user.getName());
    }

    @Override
    public void onMessage(WebSocket webSocket, String jsonMessage) {
        logger.debug("onMessage");
        logger.debug(jsonMessage);

        try {
            Message message = JsonMapper.decode(jsonMessage, Message.class);
            message.setSender(webSocket.getAttachment());

            serverState.addMessage(message);
        } catch (JsonProcessingException e) {
            logger.error("Bad messageDTO format.\n\tThe accepted format is : { content: String }");
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        logger.error("An error occurred on the server", e);
    }

    @Override
    public void onStart() {
        logger.info("Server started on ws://{}:{}", this.getAddress().getHostString(), this.getAddress().getPort());
    }
}
