package org.acme.kafka.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.kafka.entity.Order;
import org.acme.kafka.entity.OrderStatus;
import org.acme.kafka.entity.User;
import org.acme.kafka.model.OrderRequest;
import org.acme.kafka.repository.OrderRepository;
import org.acme.kafka.repository.UserRepository;

@ApplicationScoped

public class OrderService {
    @Inject
    OrderRepository orderRepository;

    @Inject
    UserRepository userRepository;
    @Transactional
    public Order createOrder (OrderRequest orderRequest){
        Order order = new Order();
        order.price = orderRequest.price;
        order.name = orderRequest.name;
        order.quantity = orderRequest.quantity;
        order.status = orderRequest.status;
        order.type = orderRequest.type;
        order.user = userRepository.findById(orderRequest.userId);
        // Lưu đối tượng Order vào cơ sở dữ liệu
        orderRepository.persist(order);
        matchOrder(order);
        return order;
    }
    public void matchOrder (Order order){
        if(order.type.equals("SELL")) {
            Order buyOrder = orderRepository.findBuyOrder(order.name, order.price);
            if (buyOrder != null) {
                if (buyOrder.quantity == order.quantity) {
                    order.status = OrderStatus.CLOSED;
                    buyOrder.status = OrderStatus.CLOSED;
                    User user = buyOrder.user;
                    buyOrder.user = order.user;
                    order.user = user;
                    orderRepository.persist(order);
                    orderRepository.persist(buyOrder);
                } else if (buyOrder.quantity > order.quantity) {
                    order.status = OrderStatus.CLOSED;
                    buyOrder.quantity = buyOrder.quantity - order.quantity;
                    orderRepository.persist(order);
                    orderRepository.persist(buyOrder);

                } else if (buyOrder.quantity < order.quantity) {
                    buyOrder.status = OrderStatus.CLOSED;
                    order.quantity = order.quantity - buyOrder.quantity;
                    User user = buyOrder.user;
                    buyOrder.user = order.user;
                    order.user = user;
                    orderRepository.persist(order);
                    orderRepository.persist(buyOrder);
                }
            }
        }
        else if(order.type.equals("BUY")){
            Order sellOrder = orderRepository.findSellOrder(order.name,order.price);
            if(sellOrder != null){
                if(sellOrder.quantity == order.quantity){
                    order.status = OrderStatus.CLOSED;
                    sellOrder.status = OrderStatus.CLOSED;
                    User user = sellOrder.user;
                    sellOrder.user = order.user;
                    order.user = user;
                    orderRepository.persist(order);
                    orderRepository.persist(sellOrder);
                }else if(sellOrder.quantity > order.quantity){
                    order.status = OrderStatus.CLOSED;
                    sellOrder.quantity = sellOrder.quantity - order.quantity;
                    User user = sellOrder.user;
                    sellOrder.user = order.user;
                    order.user = user;
                    orderRepository.persist(order);
                    orderRepository.persist(sellOrder);
                }else if(sellOrder.quantity < order.quantity){
                    sellOrder.status = OrderStatus.CLOSED;
                    order.quantity = order.quantity - sellOrder.quantity;
                    User user = sellOrder.user;
                    sellOrder.user = order.user;
                    order.user = user;
                    orderRepository.persist(order);
                    orderRepository.persist(sellOrder);
                }

            }
        }
    }

}

