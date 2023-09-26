package org.acme.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.inject.Inject;
import org.acme.kafka.entity.User;
import org.acme.kafka.service.UserService;
import org.jboss.logging.Logger;


@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    @Inject
    UserService userService;

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        LOGGER.info("Stating create user");
        LOGGER.info("Khởi tạo user :" + userService.register(new User("trieu", "trieu11112001@gmail.com", "trieu")).username);
        LOGGER.info("Khởi tạo user :" + userService.register(new User("khanh", "khanh@gmail.com", "khanh")).username);
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}