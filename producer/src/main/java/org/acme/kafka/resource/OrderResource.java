package org.acme.kafka.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.kafka.entity.Order;
import org.acme.kafka.entity.OrderMatchLog;
import org.acme.kafka.model.OrderMatchLogDTO;
import org.acme.kafka.model.OrderRequest;
import org.acme.kafka.service.OrderMatchService;
import org.acme.kafka.service.OrderService;

import java.util.List;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    @Inject
    OrderService orderService;
    @Inject
    OrderMatchService orderMatchService;


    @POST
    @Path("/create")
    @Transactional
    public Order createOrder (OrderRequest orderRequest) throws JsonProcessingException {
        return  orderService.createOrder(orderRequest);
    }
    @GET
    @Path("/buy/{productId}")
    public List<Order> findListBuyOrderOpenByProductId(@PathParam("productId") long productId) throws JsonProcessingException {
        return orderService.findListBuyOrderOpenByProductId(productId);
    }
    @GET
    @Path("/sell/{productId}")
    public List<Order> findListSellOrderOpenByProductId(@PathParam("productId") long productId) throws JsonProcessingException {
        return orderService.findListSellOrderOpenByProductId(productId);
    }

    @GET
    @Path("/match/{productId}")
    public List<OrderMatchLogDTO> orderMatchLog(@PathParam("productId") long productId) throws JsonProcessingException {
        return orderMatchService.orderMatchLog(productId);
    }
}
