package org.acme.kafka.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Order extends PanacheEntity {
    public double price;

    public String name;

    public int quantity;

    public String type;
    public OrderStatus status;

    @JsonbTransient
    @ManyToOne
    public User user;
}
