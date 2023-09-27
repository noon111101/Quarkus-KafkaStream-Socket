package org.acme.kafka.consumer;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;
import org.acme.kafka.socket.ChatSocket;
import org.eclipse.microprofile.reactive.messaging.Incoming;

/**
 * A bean consuming data from the "quote-requests" Kafka topic (mapped to "requests" channel) and giving out a random quote.
 * The result is pushed to the "quotes" Kafka topic.
 */
@ApplicationScoped
public class MessageConsumer {
    @Inject
    ChatSocket chatSocket;

    @Incoming("request")
    public void process(Record<String, String> kafkaMessage){
        System.out.println("Trace : MessageConsumer.process() : kafkaMessage = " + kafkaMessage.value());
        String key = kafkaMessage.key();
        String value = kafkaMessage.value();
        chatSocket.broadcast(">> " + key + ": " + value + "\n");
    }
}
