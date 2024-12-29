package com.rita.product_management.core.usecase.user;

import com.rita.product_management.core.common.exception.UserNotFoundException;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.user.command.AuthCommand;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import com.rita.product_management.infrastructure.security.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class AuthenticateUseCase implements UseCase<AuthCommand, AuthResponse> {

    private final JwtUtil jwtUtil;
    private final UserGateway userGateway;
    private final AuthenticationManager authenticationManager;

    public AuthenticateUseCase(AuthenticationManager authenticationManager, UserGateway userGateway, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.userGateway = userGateway;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse execute(AuthCommand command) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.username(), command.password())
        );

        User user = userGateway.findUserByUsername(command.username());

        return jwtUtil.generateToken(user);
    }
}
