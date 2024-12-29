package com.rita.product_management.dataprovider.database.entity;

import com.rita.product_management.core.domain.enums.UserType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private UserType role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public UserEntity() {
    }

    public UserEntity(Boolean active, String name, String username, String email, String password, UserType role) {
        this.active = active;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserEntity(String id, Boolean active, String username, String password, UserType role) {
        this.id = id;
        this.active = active;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public Boolean isActive() {
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

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getUpdatedAt(){
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

    @PostUpdate
    private void setUpdatedAt(){
        this.updatedAt = Instant.now().atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now().atZone(ZoneOffset.UTC).toLocalDateTime();
    }
}
