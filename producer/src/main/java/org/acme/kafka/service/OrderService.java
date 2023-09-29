package org.acme.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.kafka.entity.Order;
import org.acme.kafka.entity.OrderMatchLog;
import org.acme.kafka.entity.OrderStatus;
import org.acme.kafka.entity.User;
import org.acme.kafka.model.OrderDTO;
import org.acme.kafka.model.OrderRequest;
import org.acme.kafka.repository.OrderRepository;
import org.acme.kafka.repository.ProductRepository;
import org.acme.kafka.repository.UserRepository;
import org.acme.kafka.socket.OrderSocket;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped

public class OrderService {
    @Inject
    OrderRepository orderRepository;
    @Inject
    OrderSocket chatSocket;
    @Inject
    ProductRepository productRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    OrderMatchService orderMatchService;
    @Transactional
    public Order createOrder (OrderRequest orderRequest) throws JsonProcessingException {
        Order order = new Order();
        order.price = orderRequest.price;
        order.product = productRepository.findById(orderRequest.productId);
        order.quantity = orderRequest.quantity;
        order.status = orderRequest.status;
        order.type = orderRequest.type;
        order.user = userRepository.findById(orderRequest.userId);
        // Lưu đối tượng Order vào cơ sở dữ liệu
        orderRepository.persist(order);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.price = order.price;
        orderDTO.quantity = order.quantity;
        orderDTO.type = order.type;
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(orderDTO);
        chatSocket.broadcast(orderJson);
        matchOrder(order);
        return order;
    }
    public void matchOrder(Order order) throws JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        String time = LocalDateTime.now().format(formatter);

        List<Order> matchingOrderList = new ArrayList<>();
        User buyUser = null;
        User sellUser = null;

        if (order.type.equals("SELL")) {
            matchingOrderList = orderRepository.findBuyOrder( order.product.id,order.price, order.user.id);
        } else if (order.type.equals("BUY")) {
            matchingOrderList = orderRepository.findSellOrder( order.product.id,order.price, order.user.id);
        }

        if (!matchingOrderList.isEmpty()) {
            for(Order matchingOrder : matchingOrderList){
                if (order.type.equals("SELL")) {
                    buyUser = matchingOrder.user;
                    sellUser = order.user;
                } else if (order.type.equals("BUY")) {
                    buyUser = order.user;
                    sellUser = matchingOrder.user;
                }

                if (matchingOrder.quantity == order.quantity) {
                    order.status = OrderStatus.CLOSED;
                    matchingOrder.status = OrderStatus.CLOSED;
                    boolean isBuyerMatch = order.type.equals("SELL");
                    orderMatchService.orderMatchLog(matchingOrder, order.user.id, time, order.type, isBuyerMatch);
                    buyUser.balance -= order.price * order.quantity;
                    sellUser.balance += order.price * order.quantity;
                    matchingOrder.user = sellUser;
                    order.user = buyUser;
                } else if (matchingOrder.quantity > order.quantity) {
                    order.status = OrderStatus.CLOSED;
                    boolean isBuyerMatch = order.type.equals("SELL");
                    orderMatchService.orderMatchLog(order, matchingOrder.user.id, time, order.type, !isBuyerMatch);
                    matchingOrder.quantity -= order.quantity;
                    buyUser.balance -= order.price * order.quantity;
                    sellUser.balance += order.price * order.quantity;
                    order.user = sellUser;
                } else {
                    matchingOrder.status = OrderStatus.CLOSED;
                    boolean isBuyerMatch = order.type.equals("SELL");
                    orderMatchService.orderMatchLog(matchingOrder, order.user.id, time, order.type, isBuyerMatch);
                    order.quantity -= matchingOrder.quantity;
                    buyUser.balance -= matchingOrder.price * matchingOrder.quantity;
                    sellUser.balance += matchingOrder.price * matchingOrder.quantity;
                    matchingOrder.user = buyUser;
                }

                userRepository.persist(buyUser);
                userRepository.persist(sellUser);
                orderRepository.persist(order);
                orderRepository.persist(matchingOrder);
            }
            }
        }

        public List<Order> findListBuyOrderOpenByProductId(long productId){
            return orderRepository.findListBuyOrderOpenByProductId(productId);
        }
        public List<Order> findListSellOrderOpenByProductId(long productId){
            return orderRepository.findListSellOrderOpenByProductId(productId);
        }
    }
