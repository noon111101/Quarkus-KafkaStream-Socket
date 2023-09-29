package org.acme.kafka.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Data;
@Entity
public class Order extends PanacheEntity {
    public float price;

    public int quantity;

    public String type;
    public OrderStatus status;
    @JsonbTransient
    @ManyToOne
    public Product product;

    @JsonbTransient
    @ManyToOne
    public User user;
}
