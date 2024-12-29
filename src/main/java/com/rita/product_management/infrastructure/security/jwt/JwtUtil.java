package com.rita.product_management.infrastructure.security.jwt;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private final Key SECRET_KEY = generateSecureRandom();

    public AuthResponse generateToken(User user) {
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        LocalDateTime expiration = expirationDate.toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();

        String token = Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SECRET_KEY)
                .compact();

        return new AuthResponse(token, expiration);
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new java.util.Date());
    }

    private Key generateSecureRandom() {
        byte[] keyBytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}