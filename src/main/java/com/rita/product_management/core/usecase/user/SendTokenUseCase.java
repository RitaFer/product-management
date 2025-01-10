package com.rita.product_management.core.usecase.user;

import com.rita.product_management.core.domain.Token;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.TokenGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.user.command.SendTokenCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class SendTokenUseCase implements UnitUseCase<SendTokenCommand> {

    private final UserGateway userGateway;
    private final TokenGateway tokenGateway;
    private final EmailGateway emailGateway;

    @Override
    public void execute(SendTokenCommand command) {
        log.info("Starting token generation for username: {}", command.username());

        try {
            User user = userGateway.findActiveUserByUsername(command.username());
            Token token = tokenGateway.generateToken(user.getId());
            emailGateway.sendToken(user.getEmail(), token.getToken());

            log.info("Token sent successfully to email: {}", user.getEmail());
        } catch (HttpClientErrorException e) {
            log.error("Failed to send token to username: {}", command.username(), e);
            throw new RuntimeException("Failed to send token to user: " + command.username(), e);
        }
    }

}
