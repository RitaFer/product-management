package com.rita.product_management.core.usecase.user;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.user.command.AuthCommand;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import com.rita.product_management.infrastructure.security.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@AllArgsConstructor

public class AuthenticateUseCase implements UseCase<AuthCommand, AuthResponse> {

    private final JwtUtil jwtUtil;
    private final UserGateway userGateway;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse execute(AuthCommand command) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.username(), command.password())
        );

        User user = userGateway.findUserByUsername(command.username());

        return jwtUtil.generateToken(user);
    }
}
