package com.marvin.store.config;

import com.marvin.store.entities.Order;
import com.marvin.store.entities.User;
import com.marvin.store.entities.enums.OrderStatus;
import com.marvin.store.repositories.OrderRepository;
import com.marvin.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "ISA Brown", "isa@gmail.com", "988554488", "456798");
        User u2 = new User(null, "Jersey Gigante", "jersey@gmail.com", "955668944", "456798");

        Order o1 = new Order(null, Instant.parse("2025-06-14T19:53:07Z"), OrderStatus.WAITING_PAYMENT, u1);
        Order o2 = new Order(null, Instant.parse("2025-06-14T19:10:07Z"), OrderStatus.PAID, u2);
        Order o3 = new Order(null, Instant.parse("2025-06-14T16:53:07Z"), OrderStatus.SHIPPED, u1);

        userRepository.saveAll(Arrays.asList(u1, u2));
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));
    }
}
