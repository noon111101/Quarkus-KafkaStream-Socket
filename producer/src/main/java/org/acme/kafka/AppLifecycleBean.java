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
        productService.createProduct(new Product("AAPL", "Apple Inc.", 127.35, 214.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("MSFT", "Microsoft Corporation", 214.25, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("AMZN", "Amazon.com, Inc.", 3116.42, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("GOOG", "Alphabet Inc.", 1734.16, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("FB", "Facebook, Inc.", 276.4, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("TSLA", "Tesla, Inc.", 880.8, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("NVDA", "NVIDIA Corporation", 532.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("PYPL", "PayPal Holdings, Inc.", 234.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("ADBE", "Adobe Inc.", 485.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("NFLX", "Netflix, Inc.", 556.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("CMCSA", "Comcast Corporation", 52.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("INTC", "Intel Corporation", 57.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("CSCO", "Cisco Systems, Inc.", 45.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("PEP", "PepsiCo, Inc.", 144.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("AVGO", "Broadcom Inc.", 470.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("TXN", "Texas Instruments Incorporated", 169.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("QCOM", "QUALCOMM Incorporated", 150.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("TMUS", "T-Mobile US, Inc.", 131.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("CHTR", "Charter Communications, Inc.", 640.0, 162.2, 40.3, "Financial Services"));
        productService.createProduct(new Product("SBUX", "Starbucks Corporation", 105.0, 162.2, 40.3, "Financial Services"));
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}