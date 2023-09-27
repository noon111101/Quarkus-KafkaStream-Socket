package org.acme.kafka.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.kafka.entity.Product;
@ApplicationScoped

public class ProductRepository implements PanacheRepository<Product>{

}
