package com.rita.product_management.core.domain;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

public class Token {

    private String id;
    private String token;
    private String userId;
    private Boolean tokenUsed;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public Token() {
    }

    public Token(String userId) {
        this.createdAt = Instant.now().atZone(ZoneId.of("UTC")).toLocalDateTime();
        this.expiredAt = this.createdAt.plusHours(1);
        this.token = createToken();
        this.tokenUsed = false;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getTokenUsed() {
        return tokenUsed;
    }

    public void setTokenUsed(Boolean tokenUsed) {
        this.tokenUsed = tokenUsed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    private String createToken() {
        final Random random = new SecureRandom();
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int TOKEN_LENGTH = 6;

        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return token.toString();
    }
}

