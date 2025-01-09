package com.rita.product_management.core.usecase.user;

import com.rita.product_management.core.common.exception.AuthenticationFailedException;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.user.command.AuthCommand;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import com.rita.product_management.infrastructure.security.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class AuthenticateUseCase implements UseCase<AuthCommand, AuthResponse> {

    private final JwtUtil jwtUtil;
    private final UserGateway userGateway;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse execute(AuthCommand command) {
        log.info("Attempting authentication for user: {}", command.username());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(command.username(), command.password())
            );

            log.info("Authentication successful for user: {}", command.username());
            User user = userGateway.findActiveUserByUsername(command.username());

            return jwtUtil.generateToken(user);
        } catch (Exception e) {
            log.warn("Authentication failed for user: {}", command.username());
            throw new AuthenticationFailedException("Invalid username or password");
        }

    }

}
