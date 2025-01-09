package com.rita.product_management.core.usecase.user;

import com.rita.product_management.core.common.exception.InvalidTokenException;
import com.rita.product_management.core.domain.Token;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.TokenGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.user.command.ChangePasswordCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class ChangePasswordUseCase implements UnitUseCase<ChangePasswordCommand> {

    private final TokenGateway tokenGateway;
    private final UserGateway userGateway;

    @Override
    public void execute(ChangePasswordCommand command) {
        log.info("Starting password change process for token: {}", command.token());

        try {
            Token token = validateToken(command.token());
            log.debug("Token validated successfully for user ID: {}", token.getUserId());

            User user = userGateway.findActiveUserByUsername(token.getUserId());

            updatePassword(user, command.newPassword());

            log.info("Password updated successfully for user ID: {}", user.getId());
        } catch (Exception e) {
            log.error("Failed to change password for token: {}", command.token(), e);
            throw e;
        }
    }

    private Token validateToken(String tokenValue) {
        Optional<Token> token = tokenGateway.validateToken(tokenValue);

        if (token.isEmpty()) {
            log.warn("Invalid or expired token: {}", tokenValue);
            throw new InvalidTokenException("Invalid or expired token provided");
        }

        return token.get();
    }

    private void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userGateway.save(user);
    }

}
