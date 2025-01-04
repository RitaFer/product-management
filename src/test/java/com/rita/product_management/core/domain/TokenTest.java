package com.rita.product_management.core.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;

class TokenTest {

    private String userId;

    @BeforeEach
    void setUp() {
        userId = "testUser123";
    }

    @Test
    void givenValidUserId_whenCreatingToken_thenShouldInitializeCorrectly() {
        String userId = "user123";
        Token token = new Token(userId);
        LocalDateTime nowInUtc = Instant.now().atZone(ZoneId.of("UTC")).toLocalDateTime();

        assertThat(token).isNotNull();
        assertThat(token.getUserId()).isEqualTo(userId);
        assertThat(token.getToken()).isNotNull().hasSize(6);
        assertThat(token.getCreatedAt()).isCloseTo(nowInUtc, within(1, ChronoUnit.SECONDS));
        assertThat(token.getExpiredAt()).isEqualTo(token.getCreatedAt().plusHours(1));
        assertThat(token.getTokenUsed()).isFalse();
    }

    @Test
    void givenToken_whenCheckingExpiration_thenShouldMatchExpectedTime() {
        Token token = new Token(userId);
        LocalDateTime createdAt = token.getCreatedAt();
        LocalDateTime expiredAt = token.getExpiredAt();
        assertThat(expiredAt).isEqualTo(createdAt.plusHours(1));
    }

    @Test
    void givenNullUserId_whenCreatingToken_thenShouldThrowException() {
        assertThatThrownBy(() -> new Token(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("userId");
    }

    @Test
    void givenToken_whenGeneratingMultipleTokens_thenShouldBeUnique() {
        Token token1 = new Token(userId);
        Token token2 = new Token(userId);
        String generatedToken1 = token1.getToken();
        String generatedToken2 = token2.getToken();
        assertThat(generatedToken1).isNotEqualTo(generatedToken2);
        assertThat(generatedToken1).hasSize(6);
        assertThat(generatedToken2).hasSize(6);
    }

    @Test
    void givenToken_whenMarkingAsUsed_thenTokenUsedShouldBeTrue() {
        Token token = new Token(userId);
        token.setTokenUsed(true);
        assertThat(token.getTokenUsed()).isTrue();
    }

    @Test
    void givenExpiredToken_whenValidatingExpiration_thenShouldBeExpired() {
        Token token = Token.builder()
                .id("testId")
                .token("ABC123")
                .userId(userId)
                .tokenUsed(false)
                .createdAt(LocalDateTime.now().minusHours(2))
                .expiredAt(LocalDateTime.now().minusHours(1))
                .build();
        boolean isExpired = token.getExpiredAt().isBefore(LocalDateTime.now());
        assertThat(isExpired).isTrue();
    }

    @Test
    void givenValidToken_whenCheckingExpiration_thenShouldBeValid() {
        Token token = new Token(userId);
        boolean isExpired = token.getExpiredAt().isBefore(LocalDateTime.now());
        assertThat(isExpired).isFalse();
    }

}
