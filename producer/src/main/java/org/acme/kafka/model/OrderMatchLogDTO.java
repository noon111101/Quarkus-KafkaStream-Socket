package org.acme.kafka.model;

import lombok.Data;

@Data
public class OrderMatchLogDTO {
    public String buyUserName;
    public String sellUserName;
    public double price;
    public String nameProduct;
    public int quantity;
    public String matchTime;
    public String typeClose;
}
