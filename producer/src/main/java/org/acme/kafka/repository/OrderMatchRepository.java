package org.acme.kafka.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.kafka.entity.OrderMatchLog;

import java.util.List;

@ApplicationScoped

public class OrderMatchRepository implements PanacheRepository<OrderMatchLog> {
    public List<OrderMatchLog> findByNameProduct(String nameProduct){
        return find("nameProduct = ?1",nameProduct).list();
    }
}
