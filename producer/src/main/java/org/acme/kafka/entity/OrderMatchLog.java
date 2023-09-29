package org.acme.kafka.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
public class OrderMatchLog extends PanacheEntity {
    public Long buyUserId;
    public Long sellUserId;
    public double price;
    public String nameProduct;
    public int quantity;
    public String matchTime;
    public String typeClose;

}
