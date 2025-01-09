package com.rita.product_management.entrypoint.api.interceptor;

import com.rita.product_management.infrastructure.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class TokenInterceptor extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public TokenInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        logRequestDetails(request, authorizationHeader);

        if (isBearerToken(authorizationHeader)) {
            String token = extractToken(authorizationHeader);
            processToken(token, request);
        }

        chain.doFilter(request, response);
    }

    private void logRequestDetails(HttpServletRequest request, String authorizationHeader) {
        log.debug("Processing request: [{} {}] with Authorization header: [{}]",
                request.getMethod(), request.getRequestURI(), authorizationHeader);
    }

    private boolean isBearerToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.warn("No valid Authorization header found or it does not start with 'Bearer '");
            return false;
        }
        return true;
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }

    private void processToken(String token, HttpServletRequest request) {
        try {
            if (jwtUtil.isTokenExpired(token)) {
                log.warn("Token is expired");
                return;
            }

            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);
            log.debug("Extracted username: [{}], role: [{}]", username, role);

            setAuthentication(username, role);
        } catch (Exception e) {
            log.error("Error occurred while processing token for request: [{} {}]",
                    request.getMethod(), request.getRequestURI(), e);
        }
    }

    private void setAuthentication(String username, String role) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password("")
                .roles(role)
                .build();

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        log.info("Authentication set for username: [{}]", username);
    }
}
