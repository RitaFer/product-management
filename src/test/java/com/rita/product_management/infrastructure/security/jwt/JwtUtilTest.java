package com.rita.product_management.infrastructure.security.jwt;

import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.mocks.ProductMockProvider;
import com.rita.product_management.core.common.exception.JwtParsingException;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String secretKey = "be8e1d2d470b4d0afe07e97e84abf1be5603d19ea7d76435098f6cd4274e19e0\n";
    private final long expirationInSeconds = 3600;
    private Key secretKeyObject;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(secretKey, expirationInSeconds);
        jwtUtil.initializeSecretKey();
        secretKeyObject = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    @Test
    void givenValidUser_whenGenerateToken_thenReturnAuthResponse() {
        User user = ProductMockProvider.createUserWithRole(UserType.ADMIN);

        AuthResponse authResponse = jwtUtil.generateToken(user);

        assertNotNull(authResponse);
        assertNotNull(authResponse.token());
        assertTrue(authResponse.expiration().isAfter(LocalDateTime.now(ZoneOffset.UTC)));
    }

    @Test
    void givenValidToken_whenExtractClaims_thenReturnClaims() {
        User user = ProductMockProvider.createUserWithRole(UserType.ADMIN);
        Date expirationDate = new Date(System.currentTimeMillis() + expirationInSeconds * 1000);
        String token = createMockToken(user, expirationDate);

        Claims claims = jwtUtil.extractClaims(token);

        assertNotNull(claims);
        assertEquals(user.getUsername(), claims.getSubject());
        assertEquals("ADMIN", claims.get("role"));
    }

    @Test
    void givenInvalidToken_whenExtractClaims_thenThrowJwtParsingException() {
        String invalidToken = "invalid.jwt.token";

        assertThrows(JwtParsingException.class, () -> jwtUtil.extractClaims(invalidToken));
    }

    @Test
    void givenValidToken_whenExtractUsername_thenReturnUsername() {
        User user = ProductMockProvider.createUserWithRole(UserType.STOCKIST);
        String token = jwtUtil.generateToken(user).token();

        String username = jwtUtil.extractUsername(token);

        assertEquals(user.getUsername(), username);
    }

    @Test
    void givenValidToken_whenExtractRole_thenReturnRole() {
        User user = ProductMockProvider.createUserWithRole(UserType.STOCKIST);
        String token = jwtUtil.generateToken(user).token();

        String role = jwtUtil.extractRole(token);

        assertEquals(UserType.STOCKIST.name(), role);
    }

    @Test
    void givenValidToken_whenIsTokenExpired_thenReturnFalse() {
        User user = ProductMockProvider.createUserWithRole(UserType.STOCKIST);
        String token = jwtUtil.generateToken(user).token();

        assertFalse(jwtUtil.isTokenExpired(token));
    }

    private String createMockToken(User user, Date expirationDate) {
        return io.jsonwebtoken.Jwts.builder()
                .id("test-id")
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .setExpiration(expirationDate)
                .signWith(secretKeyObject)
                .compact();
    }
}
