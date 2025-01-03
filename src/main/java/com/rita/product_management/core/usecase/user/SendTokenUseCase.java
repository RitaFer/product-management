package com.rita.product_management.core.usecase.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rita.product_management.core.domain.Token;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.TokenGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.user.command.SendTokenCommand;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class SendTokenUseCase implements UnitUseCase<SendTokenCommand> {
    private final UserGateway userGateway;
    private final TokenGateway tokenGateway;
    private final EmailGateway emailGateway;

    public SendTokenUseCase(UserGateway userGateway, TokenGateway tokenGateway, EmailGateway emailGateway) {
        this.userGateway = userGateway;
        this.tokenGateway = tokenGateway;
        this.emailGateway = emailGateway;
    }

    @Override
    public void execute(SendTokenCommand command) throws JsonProcessingException {
        User user = userGateway.findActiveUserByUsername(command.username());
        Token token = tokenGateway.generateToken(user.getId());
        emailGateway.sendToken(user.getEmail(), token.getToken());
    }
}
