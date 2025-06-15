package com.marvin.store.config;

import com.marvin.store.entities.Category;
import com.marvin.store.entities.Order;
import com.marvin.store.entities.Product;
import com.marvin.store.entities.User;
import com.marvin.store.entities.enums.OrderStatus;
import com.marvin.store.repositories.CategoryRepository;
import com.marvin.store.repositories.OrderRepository;
import com.marvin.store.repositories.ProductRepository;
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
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "ISA Brown", "isa@gmail.com", "988554488", "456798");
        User u2 = new User(null, "Jersey Gigante", "jersey@gmail.com", "955668944", "456798");

        Category c1 = new Category(null, "Laying");
        Category c2 = new Category(null, "Breeding");
        Category c3 = new Category(null, "Slaughter");

        Product p1 = new Product(null, "Chicken Laying", "Brown egg laying hen", 129.99, "");
        Product p2 = new Product(null, "Chicken Breeding", "Some chicken breeds are referred to as dual-purpose", 119.99, "");
        Product p3 = new Product(null, "Chicken Slaughter", "Chickens that are bred solely for meat production are generally poor egg layers because these birds are faster growing", 169.99, "");
        Product p4 = new Product(null, "Chicken Laying", "Day old chicks can be purchased from hatcheries or feed stores", 129.99, "");

        Order o1 = new Order(null, Instant.parse("2025-06-14T19:53:07Z"), OrderStatus.WAITING_PAYMENT, u1);
        Order o2 = new Order(null, Instant.parse("2025-06-14T19:10:07Z"), OrderStatus.PAID, u2);
        Order o3 = new Order(null, Instant.parse("2025-06-14T16:53:07Z"), OrderStatus.SHIPPED, u1);

        userRepository.saveAll(Arrays.asList(u1, u2));
        categoryRepository.saveAll(Arrays.asList(c1, c2, c3));

        p1.getCategories().add(c1);
        p2.getCategories().add(c1);
        p2.getCategories().add(c3);
        p3.getCategories().add(c3);
        p4.getCategories().add(c3);

        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4));

        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4));
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));

    }
}
