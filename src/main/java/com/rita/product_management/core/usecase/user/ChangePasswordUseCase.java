package com.rita.product_management.core.usecase.user;

import com.rita.product_management.core.common.exception.BusinessException;
import com.rita.product_management.core.domain.Token;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.TokenGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.user.command.ChangePasswordCommand;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class ChangePasswordUseCase implements UnitUseCase<ChangePasswordCommand> {
    private final TokenGateway tokenGateway;
    private final UserGateway userGateway;

    public ChangePasswordUseCase(TokenGateway tokenGateway, UserGateway userGateway) {
        this.tokenGateway = tokenGateway;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(ChangePasswordCommand command) {
        Boolean isValidToken = tokenGateway.validateToken(command.token());
        Boolean isValidPassword = validatePassword(command);

        if (isValidToken && isValidPassword){
            Token token = tokenGateway.findToken(command.token());
            User user = userGateway.findActiveUserByUsername(token.getUserId());
            user.setPassword(command.password1());
            userGateway.save(user);
        } else {
            throw new BusinessException("Invalid information provided");
        }
    }

    private Boolean validatePassword(ChangePasswordCommand command){
        return command.password1().equals(command.password2());
    }

}
