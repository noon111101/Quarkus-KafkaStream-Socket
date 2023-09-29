package org.acme.kafka.socket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.jboss.logging.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/orderLog/{username}")
@ApplicationScoped
public class OrderLogSocket {

    private static final Logger LOG = Logger.getLogger(OrderSocket.class);

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessions.put(username, session);
    }


    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        broadcast(message);
    }


    public void broadcast(String message ) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendText(message, result -> {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }


}