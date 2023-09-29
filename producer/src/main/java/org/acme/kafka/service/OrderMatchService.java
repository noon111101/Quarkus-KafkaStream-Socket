package org.acme.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.kafka.entity.Order;
import org.acme.kafka.entity.OrderMatchLog;
import org.acme.kafka.model.OrderMatchLogDTO;
import org.acme.kafka.repository.OrderMatchRepository;
import org.acme.kafka.repository.ProductRepository;
import org.acme.kafka.repository.UserRepository;
import org.acme.kafka.socket.OrderLogSocket;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped

public class OrderMatchService {
    @Inject
    OrderMatchRepository orderMatchRepository;
    @Inject
    OrderLogSocket orderLogSocket;
    @Inject
    UserRepository userRepository;
    @Inject
    ProductRepository productRepository;

    public void orderMatchLog(Order orderClose , Long userId , String matchTime , String type , boolean check) throws JsonProcessingException {
        OrderMatchLog orderMatchLog = new OrderMatchLog();
        orderMatchLog.price = orderClose.price;
        orderMatchLog.quantity = orderClose.quantity;
        orderMatchLog.nameProduct = orderClose.product.symbol;
        orderMatchLog.matchTime = matchTime;
        orderMatchLog.typeClose = type;
        if(check){
            orderMatchLog.buyUserId = orderClose.user.id;
            orderMatchLog.sellUserId = userId;
        }
        else {
            orderMatchLog.buyUserId = userId;
            orderMatchLog.sellUserId =orderClose.user.id;
        }
        orderMatchRepository.persist(orderMatchLog);
        OrderMatchLogDTO orderMatchLogDTO = new OrderMatchLogDTO();
        orderMatchLogDTO.price = orderMatchLog.price;
        orderMatchLogDTO.quantity = orderMatchLog.quantity;
        orderMatchLogDTO.nameProduct = orderMatchLog.nameProduct;
        orderMatchLogDTO.matchTime = orderMatchLog.matchTime;
        orderMatchLogDTO.buyUserName = userRepository.findById(orderMatchLog.buyUserId).username;
        orderMatchLogDTO.sellUserName = userRepository.findById(orderMatchLog.sellUserId).username;
        orderMatchLogDTO.typeClose = type;
        ObjectMapper objectMapper = new ObjectMapper();
        String orderLogJson = objectMapper.writeValueAsString(orderMatchLogDTO);
        orderLogSocket.broadcast(orderLogJson);
    }
    // service for findByNameProduct
    public List<OrderMatchLogDTO> orderMatchLog(Long productId) throws JsonProcessingException {
        List<OrderMatchLogDTO> orderMatchLogDTOList = new ArrayList<>();
        List<OrderMatchLog> orderMatchLog = orderMatchRepository.findByNameProduct(productRepository.findByIdOptional(productId).get().symbol);
        for (OrderMatchLog o : orderMatchLog){
            OrderMatchLogDTO orderMatchLogDTO = new OrderMatchLogDTO();
            orderMatchLogDTO.price = o.price;
            orderMatchLogDTO.quantity = o.quantity;
            orderMatchLogDTO.nameProduct = o.nameProduct;
            orderMatchLogDTO.matchTime = o.matchTime;
            orderMatchLogDTO.buyUserName = userRepository.findById(o.buyUserId).username;
            orderMatchLogDTO.sellUserName = userRepository.findById(o.sellUserId).username;
            orderMatchLogDTO.typeClose = o.typeClose;
            orderMatchLogDTOList.add(orderMatchLogDTO);
        }
        return orderMatchLogDTOList;
    }
}





