package org.acme.kafka.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.kafka.entity.User;
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
