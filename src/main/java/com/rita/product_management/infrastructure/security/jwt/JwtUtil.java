package com.rita.product_management.infrastructure.security.jwt;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtil {

    private final Key SECRET_KEY = generateSecureRandom();

    public AuthResponse generateToken(User user) {
        log.debug("Generating JWT for user: [{}], role: [{}]", user.getUsername(), user.getRole());

        try {
            Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
            LocalDateTime expiration = expirationDate.toInstant()
                    .atZone(ZoneOffset.UTC)
                    .toLocalDateTime();

            String token = Jwts.builder()
                    .id(UUID.randomUUID().toString())
                    .subject(user.getUsername())
                    .claim("role", user.getRole())
                    .expiration(expirationDate)
                    .signWith(SECRET_KEY)
                    .compact();

            log.debug("JWT successfully generated for user: [{}], expiration: [{}]", user.getUsername(), expiration);
            return new AuthResponse(token, expiration);

        } catch (Exception e) {
            log.error("Error occurred while generating JWT for user: [{}]", user.getUsername(), e);
            throw new RuntimeException("Failed to generate JWT", e);
        }
    }

    public Claims extractClaims(String token) {
        log.debug("Extracting claims from JWT: [{}]", token);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.debug("Claims successfully extracted from JWT: [{}]", claims);
            return claims;

        } catch (Exception e) {
            log.error("Error occurred while extracting claims from JWT: [{}]", token, e);
            throw new RuntimeException("Failed to extract claims from JWT", e);
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
        log.debug("Checking if JWT is expired: [{}]", token);

        try {
            boolean isExpired = extractClaims(token).getExpiration().before(new Date());
            log.debug("JWT expiration status: [{}] (true = expired, false = valid)", isExpired);
            return isExpired;

        } catch (Exception e) {
            log.error("Error occurred while checking expiration of JWT: [{}]", token, e);
            throw new RuntimeException("Failed to check expiration of JWT", e);
        }
    }

    private Key generateSecureRandom() {
        log.debug("Generating secure random key for JWT signing...");
        byte[] keyBytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);

        Key key = Keys.hmacShaKeyFor(keyBytes);
        log.debug("Secure random key successfully generated.");
        return key;
    }

}
