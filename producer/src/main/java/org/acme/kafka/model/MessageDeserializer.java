package org.acme.kafka.model;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class MessageDeserializer extends ObjectMapperDeserializer<Message> {
    public MessageDeserializer() {
        super(Message.class);
    }
}
