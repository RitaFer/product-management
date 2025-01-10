package com.rita.product_management.core.common.util;

import com.rita.product_management.dataprovider.database.entity.UserEntity;
import com.rita.product_management.dataprovider.database.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Slf4j
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            log.info("Starting database initialization...");

            Optional<UserEntity> adminUser = userRepository.findByUsername("admin");
            Optional<UserEntity> stockistUser = userRepository.findByUsername("stockist");

            adminUser.ifPresent(user -> {
                adminUser.get().setPassword(passwordEncoder.encode("Admin@123"));
                userRepository.save(user);
            });

            stockistUser.ifPresent(user -> {
                stockistUser.get().setPassword(passwordEncoder.encode("Stockist@123"));
                userRepository.save(user);
            });

            log.info("Default users initialized successfully!");
        };
    }

}
