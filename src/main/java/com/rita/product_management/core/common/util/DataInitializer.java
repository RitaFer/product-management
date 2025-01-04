package com.rita.product_management.core.common.util;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import com.rita.product_management.core.domain.enums.UserType;
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
                log.warn("Admin user already exists. Deleting existing user...");
                userRepository.delete(user);
            });

            stockistUser.ifPresent(user -> {
                log.warn("Stockist user already exists. Deleting existing user...");
                userRepository.delete(user);
            });

            UserEntity admin = new UserEntity();
            admin.setActive(true);
            admin.setName("First Admin User");
            admin.setUsername("admin");
            admin.setEmail("rialf.ferreira@gmail.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(UserType.ADMIN);
            userRepository.save(admin);
            log.info("Admin user created: {}", admin.getId());

            UserEntity stockist = new UserEntity();
            stockist.setActive(true);
            stockist.setName("First Stockist User");
            stockist.setUsername("stockist");
            stockist.setEmail("iwjkwoods@gmail.com");
            stockist.setPassword(passwordEncoder.encode("Stockist@123"));
            stockist.setRole(UserType.STOCKIST);
            userRepository.save(stockist);
            log.info("Stockist user created: {}", stockist.getId());

            log.info("Default users initialized successfully!");
        };
    }

}
