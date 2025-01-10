package com.rita.product_management.infrastructure.security.jwt;

import com.rita.product_management.core.common.exception.JwtParsingException;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtil {

    private final String secretKeyString;
    private final long expirationInSeconds;
    private Key SECRET_KEY;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKeyString,
            @Value("${jwt.expiration:3600}") long expirationInSeconds
    ) {
        if (secretKeyString == null || secretKeyString.isEmpty()) {
            throw new IllegalArgumentException("JWT secret key must be configured");
        }

        this.secretKeyString = secretKeyString;
        this.expirationInSeconds = expirationInSeconds;
    }

    @PostConstruct
    public void initializeSecretKey() {
        log.info("Initializing JWT secret key...");
        SECRET_KEY = new SecretKeySpec(
                secretKeyString.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );
        log.info("JWT SECRET_KEY initialized successfully");
    }

    public AuthResponse generateToken(User user) {
        log.debug("Generating JWT for user: [{}]", user.getUsername());

        Date expirationDate = calculateExpirationDate();
        String token = createJwtToken(user, expirationDate);

        log.debug("JWT generated successfully for user: [{}], expires at: [{}]", user.getUsername(), expirationDate);
        return new AuthResponse(token, expirationDate.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime());
    }

    private Date calculateExpirationDate() {
        return new Date(System.currentTimeMillis() + expirationInSeconds * 1000);
    }

    private String createJwtToken(User user, Date expirationDate) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    public Claims extractClaims(String token) {
        log.debug("Extracting claims from JWT...");

        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Error extracting claims from token", e);
            throw new JwtParsingException("Failed to extract claims");
        }
    }

    public String extractUsername(String token) {
        log.debug("Extracting username from JWT: [{}]", token);
        String username = extractClaims(token).getSubject();
        log.debug("Username extracted from JWT: [{}]", username);
        return username;
    }

    public String extractRole(String token) {
        log.debug("Extracting role from JWT: [{}]", token);
        String role = (String) extractClaims(token).get("role");
        log.debug("Role extracted from JWT: [{}]", role);
        return role;
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}

