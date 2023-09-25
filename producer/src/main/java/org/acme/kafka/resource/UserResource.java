package org.acme.kafka.resource;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.kafka.model.LoginRequest;
import org.acme.kafka.entity.User;
import org.acme.kafka.security.TokenService;
import org.acme.kafka.service.UserService;


@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService service;
    @POST
    @Path("/register")
    @Transactional
    public User register(User user) {
       return service.register(user);
    }

    @POST
    @Path("/login")
    public String login(LoginRequest loginRequest) {
       return service.login(loginRequest);
    }

}
