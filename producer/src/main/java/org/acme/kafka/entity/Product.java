package org.acme.kafka.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Product extends PanacheEntity {
    public String symbol; // Ký hiệu của cổ phiếu (Ví dụ: AAPL cho Apple Inc.)
    public String companyName; // Tên công ty phát hành cổ phiếu
    public double currentPrice; // Giá hiện tại của cổ phiếu
    public double marketCap; // Vốn hóa thị trường của công ty
    public double peRatio;

    public String economic; //nghành kinh tế
    @JsonbTransient
    @OneToMany(mappedBy = "product")
    public List<Order> orders;

    public Product(String symbol, String companyName, double currentPrice, double marketCap, double peRatio, String economic) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.marketCap = marketCap;
        this.peRatio = peRatio;
        this.economic = economic;
    }

    public Product() {

    }
}
