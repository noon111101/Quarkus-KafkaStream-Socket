package org.acme.kafka.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.kafka.entity.Order;

import java.util.List;

@ApplicationScoped

public class OrderRepository implements PanacheRepository<Order> {
    public Order findSellOrder(String name, double price){
        return find("name = ?1 and price = ?2 and type = 'SELL' and status = 0",name,price).firstResult();
    }
    public Order findBuyOrder(String name, double price){
        return find("name = ?1 and price = ?2 and type = 'BUY' and status = 0",name,price).firstResult();
    }
    public List<Order> findListOrderOpen(){
        return find("status = 0").list();
    }


}
