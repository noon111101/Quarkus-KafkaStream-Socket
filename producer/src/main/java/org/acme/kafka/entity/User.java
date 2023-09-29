package org.acme.kafka.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
public class User extends PanacheEntity {
    public String username;
    public String email;

    @JsonbTransient
    public String password;

    public float balance;
    @JsonbTransient
    @OneToMany(mappedBy = "user")
    public List<Order> orders;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public User() {

    }
}
