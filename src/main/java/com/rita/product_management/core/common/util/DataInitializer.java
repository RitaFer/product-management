package com.rita.product_management.core.common.util;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.dataprovider.database.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<UserEntity> adminUser = userRepository.findByUsername("admin");
            Optional<UserEntity> stockistUser = userRepository.findByUsername("stockist");

            adminUser.ifPresent(userRepository::delete);
            stockistUser.ifPresent(userRepository::delete);

            UserEntity admin = new UserEntity();
            admin.setActive(true);
            admin.setName("First Admin User");
            admin.setUsername("admin");
            admin.setEmail("rialf.ferreira@gmail.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(UserType.ADMIN);
            userRepository.save(admin);

            UserEntity stockist = new UserEntity();
            stockist.setActive(true);
            stockist.setName("First Stockist User");
            stockist.setUsername("stockist");
            stockist.setEmail("iwjkwoods@gmail.com");
            stockist.setPassword(passwordEncoder.encode("Stockist@123"));
            stockist.setRole(UserType.STOCKIST);
            userRepository.save(stockist);

            System.out.print("[DataInitializer] Default users initialized!");
        };
    }
}

