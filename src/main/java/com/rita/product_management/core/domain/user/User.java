package com.rita.product_management.core.domain.user;

import com.rita.product_management.core.domain.enums.UserType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Random;

import static com.rita.product_management.core.common.util.RandomPasswordGenerator.generatePassword;

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

    public User() {
    }

    public User(String name, String email, UserType role) {
        this.name = name;
        this.active = false;
        this.username = generateUsername(name);
        this.email = email;
        this.password = generatePassword();
        this.role = role;
        this.createdAt = Instant.now().atZone(ZoneOffset.UTC).toLocalDateTime();;
        this.updatedAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public Boolean isActive(){
        return active;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserType getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }

    public static String generateUsername(String name) {
        String baseName = name.toLowerCase().replaceAll("\\s+", "");

        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900);

        String[] usernameOptions = {
                baseName + randomNumber,
                randomNumber + baseName,
        };

        int optionIndex = random.nextInt(usernameOptions.length);
        return usernameOptions[optionIndex];
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        User that = (User) o;

        return Objects.equals(username, that.username) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role);
    }
}
