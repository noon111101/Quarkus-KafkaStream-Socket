package org.acme.kafka.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.kafka.entity.Order;

import java.util.List;

@ApplicationScoped

public class OrderRepository implements PanacheRepository<Order> {
    public List<Order> findSellOrder(Long productId, double price, long userId){
        return find("product.id = ?1 and price = ?2 and type = 'SELL' and status = 0 and user.id != ?3",productId,price,userId).list();
    }
    //findBuyOrder with userid != userid
    public List<Order> findBuyOrder(Long productId, double price, long userId){
        return find("product.id = ?1 and price = ?2 and type = 'BUY' and status = 0 and user.id != ?3",productId,price,userId).list();
    }

    public List<Order> findListBuyOrderOpenByProductId(long productId){
        return find("product.id = ?1 and type = 'BUY' and status = 0 order by id desc",productId).page(0,3).list();
    }
    public List<Order> findListSellOrderOpenByProductId(long productId){
        return find("product.id = ?1 and type = 'SELL' and status = 0 order by id desc",productId).page(0,3).list();
    }

}
