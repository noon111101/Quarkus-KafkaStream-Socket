package org.acme.kafka.model;

import org.acme.kafka.entity.OrderStatus;

public class OrderRequest {
    public double price;

    public String name;

    public int quantity;

    public String type;
    public OrderStatus status;
    public Long userId;

}
