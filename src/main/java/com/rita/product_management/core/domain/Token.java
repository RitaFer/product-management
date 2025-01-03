package com.rita.product_management.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    private String id;
    private String token;
    private String userId;
    private Boolean tokenUsed;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public Token(String userId) {
        log.info("Creating new Token for userId: [{}]", userId);
        this.createdAt = Instant.now().atZone(ZoneId.of("UTC")).toLocalDateTime();
        this.expiredAt = this.createdAt.plusHours(1);
        this.token = createToken();
        log.debug("Generated token: [{}]", this.token);
        this.tokenUsed = false;
        this.userId = userId;
        log.info("Token created successfully for userId: [{}]. Expiration: [{}]", userId, this.expiredAt);
    }

    private String createToken() {
        log.debug("Generating random token...");
        final Random random = new SecureRandom();
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int TOKEN_LENGTH = 6;

        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        log.debug("Token generated successfully: [{}]", token);
        return token.toString();
    }

}
