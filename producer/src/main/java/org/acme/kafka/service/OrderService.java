package org.acme.kafka.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.kafka.entity.Order;
import org.acme.kafka.entity.OrderStatus;
import org.acme.kafka.entity.User;
import org.acme.kafka.model.OrderRequest;
import org.acme.kafka.repository.OrderRepository;
import org.acme.kafka.repository.ProductRepository;
import org.acme.kafka.repository.UserRepository;

@ApplicationScoped

public class OrderService {
    @Inject
    OrderRepository orderRepository;

    @Inject
    ProductRepository productRepository;
    @Inject
    UserRepository userRepository;
    @Transactional
    public Order createOrder (OrderRequest orderRequest){
        Order order = new Order();
        order.price = orderRequest.price;
        order.product = productRepository.findById(orderRequest.productId);
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
               User buyUser = buyOrder.user;
               User sellUser = order.user;
                if (buyOrder.quantity == order.quantity) {
                    order.status = OrderStatus.CLOSED;
                    buyOrder.status = OrderStatus.CLOSED;
                    buyOrder.user = sellUser;
                    order.user = buyUser;
                    buyUser.balance = buyUser.balance - buyOrder.price * buyOrder.quantity;
                    sellUser.balance = sellUser.balance + buyOrder.price * buyOrder.quantity;

                } else if (buyOrder.quantity > order.quantity) {
                    order.status = OrderStatus.CLOSED;
                    buyOrder.quantity = buyOrder.quantity - order.quantity;
                    buyUser.balance = buyUser.balance - order.price * order.quantity;
                    sellUser.balance = sellUser.balance + order.price * order.quantity;
                    order.user = buyUser;
                } else if (buyOrder.quantity < order.quantity) {
                    buyOrder.status = OrderStatus.CLOSED;
                    order.quantity = order.quantity - buyOrder.quantity;
                    buyUser.balance = buyUser.balance - buyOrder.price * buyOrder.quantity;
                    sellUser.balance = sellUser.balance + buyOrder.price * buyOrder.quantity;
                }
                userRepository.persist(buyUser);
                userRepository.persist(sellUser);
                orderRepository.persist(order);
                orderRepository.persist(buyOrder);
            }
        }
        else if(order.type.equals("BUY")){
            Order sellOrder = orderRepository.findSellOrder(order.name,order.price);
            if(sellOrder != null){
                User buyUser = order.user;
                User sellUser = sellOrder.user;
                if(sellOrder.quantity == order.quantity){
                    order.status = OrderStatus.CLOSED;
                    sellOrder.status = OrderStatus.CLOSED;
                    sellOrder.user = buyUser;
                    order.user = sellUser;
                    buyUser.balance = buyUser.balance - sellOrder.price * sellOrder.quantity;
                    sellUser.balance = sellUser.balance + sellOrder.price * sellOrder.quantity;

                }else if(sellOrder.quantity > order.quantity){
                    order.status = OrderStatus.CLOSED;
                    sellOrder.quantity = sellOrder.quantity - order.quantity;
                    buyUser.balance = buyUser.balance - order.price * order.quantity;
                    sellUser.balance = sellUser.balance + order.price * order.quantity;
                    order.user = sellUser;
                }else if(sellOrder.quantity < order.quantity){
                    sellOrder.status = OrderStatus.CLOSED;
                    order.quantity = order.quantity - sellOrder.quantity;
                    buyUser.balance = buyUser.balance - sellOrder.price * sellOrder.quantity;
                    sellUser.balance = sellUser.balance + sellOrder.price * sellOrder.quantity;
                    sellOrder.user = buyUser;
                }
                userRepository.persist(buyUser);
                userRepository.persist(sellUser);
                orderRepository.persist(order);
                orderRepository.persist(sellOrder);
            }
        }
    }

}

