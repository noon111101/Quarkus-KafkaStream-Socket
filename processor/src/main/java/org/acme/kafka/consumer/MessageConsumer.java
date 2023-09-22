package org.acme.kafka.consumer;

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;
import org.acme.kafka.model.Message;
import org.acme.kafka.socket.ChatSocket;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.net.Socket;

/**
 * A bean consuming data from the "quote-requests" Kafka topic (mapped to "requests" channel) and giving out a random quote.
 * The result is pushed to the "quotes" Kafka topic.
 */
@ApplicationScoped
public class MessageConsumer {
    @Inject
    ChatSocket chatSocket;

    @Incoming("request")
    public void process(Message message) throws InterruptedException {
        System.out.println("TRACE_1 " + message);
        chatSocket.broadcast(">> " + message.getUsername() + ": " + message.message);
    }
}
