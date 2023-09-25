package org.acme.kafka.resource;

import java.util.UUID;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.acme.kafka.model.Message;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.smallrye.mutiny.Multi;

@Path("/send")
public class MessageResource {

    @Channel("send-request")
    Emitter<Record<String, String>> messageEmitter;

    /**
     * Endpoint to generate a new quote request id and send it to "quote-requests" Kafka topic using the emitter.
     */
    @POST
    @Path("/request")
    @Produces(MediaType.APPLICATION_JSON)
    public Message createRequest(Message message) {
        messageEmitter.send(Record.of(message.getUsername(), message.message));
        return message;
    }

}
