package org.acme.kafka.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.kafka.model.Message;
import org.acme.kafka.stream.InteractiveQueries;

import java.util.Map;

@Path("/stream")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StreamResource {
    @Inject
    InteractiveQueries interactiveQueries;
    @GET
    @Path("/get")
    public Map<String, Message> getMessageResult(){
        return interactiveQueries.getMessageResult();
    }

}
