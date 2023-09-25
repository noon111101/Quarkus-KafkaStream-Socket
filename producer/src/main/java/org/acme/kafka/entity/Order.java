package org.acme.kafka.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.apache.kafka.common.protocol.types.Field;

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
