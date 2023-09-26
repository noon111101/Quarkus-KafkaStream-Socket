package org.acme.kafka.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.kafka.stream.InteractiveQueries;


@Path("/stream")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StreamResource {
    @Inject
    InteractiveQueries interactiveQueries;
    @GET
    @Path("/get")
    public String getMessageResult(@QueryParam("username") String username) {
        return interactiveQueries.getMessage(username);
    }

}
