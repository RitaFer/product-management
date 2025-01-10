package com.rita.product_management.infrastructure.security.jwt;

import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil("CzsRHbH7aXfMvdT9DbPPtSVSesvfFRUDQjMVjJBeeA0ps2qItTpZWMccQpUzmwl9ufz9sW6e-aw48WZnCo4GnQ", 3600);
        jwtUtil.initializeSecretKey();
    }

    @Test
    void givenValidUserWhenGenerateTokenThenReturnValidAuthResponse() {
        User user = User.builder().username("testUser").role(UserType.ADMIN).build();

        AuthResponse authResponse = jwtUtil.generateToken(user);

        assertNotNull(authResponse.token());
        assertNotNull(authResponse.expiration());
        assertTrue(authResponse.expiration().isAfter(LocalDateTime.now()));
    }

    @Test
    void givenValidTokenWhenExtractClaimsThenReturnValidClaims() {
        User user = User.builder().username("testUser").role(UserType.ADMIN).build();

        AuthResponse authResponse = jwtUtil.generateToken(user);

        Claims claims = jwtUtil.extractClaims(authResponse.token());

        assertEquals("testUser", claims.getSubject());
        assertEquals("ADMIN", claims.get("role"));
    }

//    @Test
//    void givenValidTokenWhenExtractUsernameThenReturnUsername() {
//        User user = User.builder().username("testUser").role(UserType.ADMIN).build();
//
//        AuthResponse authResponse = jwtUtil.generateToken(user);
//
//        String username = jwtUtil.extractUsername(authResponse.token());
//
//        assertEquals("testUser", username);
//    }
//
//    @Test
//    void givenValidTokenWhenExtractRoleThenReturnRole() {
//        User user = User.builder().username("testUser").role(UserType.ADMIN).build();
//
//        AuthResponse authResponse = jwtUtil.generateToken(user);
//
//        String role = jwtUtil.extractRole(authResponse.token());
//
//        assertEquals("ADMIN", role);
//    }

    @Test
    void givenExpiredTokenWhenCheckTokenExpirationThenReturnFalse() {
        User user = User.builder().username("testUser").role(UserType.ADMIN).build();

        AuthResponse authResponse = jwtUtil.generateToken(user);

        boolean isExpired = jwtUtil.isTokenExpired(authResponse.token());

        assertFalse(isExpired);
    }

    @Test
    void givenValidTokenWhenCheckTokenExpirationThenReturnFalse() {
        User user = User.builder().username("testUser").role(UserType.ADMIN).build();

        AuthResponse authResponse = jwtUtil.generateToken(user);

        boolean isExpired = jwtUtil.isTokenExpired(authResponse.token());

        assertFalse(isExpired);
    }

}
