package com.rita.product_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProductManagementApplication {
//	TODO: Integration tests for controllers and external gateways

    public static void main(String[] args) {
        SpringApplication.run(ProductManagementApplication.class, args);
    }

}
