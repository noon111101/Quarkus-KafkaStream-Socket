package org.acme.kafka.model;

import lombok.Data;

@Data
public class OrderMessage {
    public double price;

    public String name;
    public String status;

    public Long userId;
}
