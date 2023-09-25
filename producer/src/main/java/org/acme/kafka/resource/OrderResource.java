package org.acme.kafka.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.kafka.entity.Order;
import org.acme.kafka.model.OrderRequest;
import org.acme.kafka.service.OrderService;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    @Inject
    OrderService orderService;


    @POST
    @Path("/create")
    @Transactional
    public Order createOrder (OrderRequest orderRequest){
        return  orderService.createOrder(orderRequest);
    }

}
