package org.acme.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.kafka.entity.Product;
import org.acme.kafka.entity.User;
import org.acme.kafka.repository.ProductRepository;
import org.acme.kafka.repository.UserRepository;
import org.acme.kafka.service.ProductService;
import org.acme.kafka.service.UserService;
import org.jboss.logging.Logger;


@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    @Inject
    UserService userService;
    @Inject
    ProductService productService;

    @Inject
    UserRepository userRepository;
    @Transactional
    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        LOGGER.info("Stating create user");
        User user1 = userService.register(new User("trieu", "trieu11112001@gmail.com", "trieu"));
        user1.setBalance(1000000);
        userRepository.persist(user1);
        User user2 = userService.register(new User("khanh", "khanh@gmail.com", "khanh"));
        user2.setBalance(2000000);
        userRepository.persist(user2);
        productService.createProduct(new Product("AAPL", "Apple Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("MSFT", "Microsoft Corporation", 214.25, 162.2, 40.3, 5.6, "Financial Services"));
        productService.createProduct(new Product("GOOG", "Alphabet Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("AMZN", "Amazon.com, Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("FB", "Facebook, Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("TSLA", "Tesla, Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("NVDA", "NVIDIA Corporation", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("PYPL", "PayPal Holdings, Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("INTC", "Intel Corporation", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("CMCSA", "Comcast Corporation", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("NFLX", "Netflix, Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("ADBE", "Adobe Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("CSCO", "Cisco Systems, Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("PEP", "PepsiCo, Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("AVGO", "Broadcom Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("TXN", "Texas Instruments Incorporated", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("QCOM", "QUALCOMM Incorporated", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("TMUS", "T-Mobile US, Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
        productService.createProduct(new Product("CHTR", "Charter Communications, Inc.", 127.35, 214.2, 40.3, -2.5, "Financial Services"));
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}