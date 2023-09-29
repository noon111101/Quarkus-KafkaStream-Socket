package org.acme.kafka.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.acme.kafka.entity.User;
import org.acme.kafka.model.LoginRequest;
import org.acme.kafka.repository.UserRepository;
import org.acme.kafka.security.TokenService;

@ApplicationScoped

public class UserService {
    @Inject
    TokenService service;
    @Inject
    UserRepository userRepository;
    @Transactional
    public User register(User user) {
        // Băm mật khẩu trước khi lưu vào cơ sở dữ liệu
        String hashedPassword = hashPassword(user.password);
        user.password = hashedPassword;

        user.persist(); // Lưu người dùng vào cơ sở dữ liệu
        return user;
    }
    public String login(LoginRequest loginRequest) {
        User existingUser = User.find("username", loginRequest.getUsername()).firstResult();
        if (existingUser == null || !verifyPassword(loginRequest.getPassword(), existingUser.password)) {
            throw new WebApplicationException(Response.status(404).entity("No user found or password is incorrect").build());
        }
        return service.generateUserToken(existingUser.email, loginRequest.getPassword());
    }

    private String hashPassword(String plainTextPassword) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        return hasher.hashToString(12, plainTextPassword.toCharArray()); // 12 là số vòng lặp (log rounds)
    }

    private boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(plainTextPassword.toCharArray(), hashedPassword);
        return result.verified;
    }
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }
}
