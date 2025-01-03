package com.rita.product_management.core.domain.user;

import com.rita.product_management.core.common.util.RandomPasswordGenerator;
import com.rita.product_management.core.domain.enums.UserType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Random;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private Boolean active;
    private String name;
    private String username;
    private String email;
    private String password;
    private UserType role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String name, String email, UserType role) {
        log.info("Creating User with name: [{}], email: [{}], and role: [{}]", name, email, role);
        this.name = name;
        this.active = false;
        this.username = generateUsername(name);
        this.email = email;
        this.password = generatePassword();
        log.debug("Generated password for user [{}]: [HIDDEN]", this.username);
        this.role = role;
        this.createdAt = Instant.now().atZone(ZoneOffset.UTC).toLocalDateTime();
        this.updatedAt = createdAt;
        log.info("User [{}] created successfully.", this.username);
    }

    public static String generateUsername(String name) {
        log.debug("Generating username for name: [{}]", name);
        String baseName = name.toLowerCase().replaceAll("\\s+", "");
        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900);

        String[] usernameOptions = {
                baseName + randomNumber,
                randomNumber + baseName,
        };

        int optionIndex = random.nextInt(usernameOptions.length);
        String generatedUsername = usernameOptions[optionIndex];
        log.debug("Generated username: [{}]", generatedUsername);
        return generatedUsername;
    }

    public static String generatePassword() {
        log.debug("Generating random password for new user...");
        return RandomPasswordGenerator.generatePassword();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        boolean isEqual = Objects.equals(username, user.username) && role == user.role;
        log.debug("Comparing User [{}] with another User [{}]: Equal = [{}]", this.username, user.username, isEqual);
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hashCode = Objects.hash(username, role);
        log.debug("Generated hashCode for User [{}]: [{}]", this.username, hashCode);
        return hashCode;
    }

}

