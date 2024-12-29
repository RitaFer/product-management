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
            Optional<UserEntity> stockistUser = userRepository.findByUsername("admin");

            adminUser.ifPresent(userRepository::delete);
            stockistUser.ifPresent(userRepository::delete);

            if (adminUser.isEmpty()) {
                UserEntity admin = new UserEntity(true, "First Admin User", "admin", "rialf.ferreira@gmail.com", passwordEncoder.encode("Admin@123"), UserType.ADMIN);
                userRepository.save(admin);
            }

            if (stockistUser.isEmpty()) {
                UserEntity stockist = new UserEntity(true, "First Stockist User", "stockist", "iwjkwoods@gmail.com", passwordEncoder.encode("Stockist@123"), UserType.STOCKIST);
                userRepository.save(stockist);
            }

            System.out.print("[DataInitializer] Default users initialized!");
        };
    }
}

