package org.acme.kafka.model;

import org.acme.kafka.entity.OrderStatus;

public class OrderRequest {
    public float price;

    public Long productId;

    public int quantity;

    public String type;
    public OrderStatus status;
    public Long userId;

}
