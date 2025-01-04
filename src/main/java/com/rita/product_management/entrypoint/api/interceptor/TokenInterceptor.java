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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        log.debug("Processing request: [{} {}] with Authorization header: [{}]", request.getMethod(), request.getRequestURI(), authorizationHeader);

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                log.debug("Extracted token: [{}]", token);

                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);
                log.debug("Extracted username: [{}], role: [{}]", username, role);

                if (!jwtUtil.isTokenExpired(token)) {
                    log.info("Token is valid for username: [{}]", username);

                    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                            .username(username)
                            .password("")
                            .roles(role)
                            .build();

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("Authentication set for username: [{}]", username);
                } else log.warn("Token is expired for username: [{}]", username);
            } else log.warn("No valid Authorization header found or it does not start with 'Bearer '");

        } catch (Exception e) {
            log.error("Error occurred while processing token for request: [{} {}]", request.getMethod(), request.getRequestURI(), e);
        }

        chain.doFilter(request, response);
    }

}
