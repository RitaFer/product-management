package com.rita.product_management.core.usecase.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.account.command.DeleteAccountCommand;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class DeleteAccountUseCase implements UnitUseCase<DeleteAccountCommand> {

    private final UserGateway userGateway;
    private final EmailGateway emailGateway;

    public DeleteAccountUseCase(UserGateway userGateway, EmailGateway emailGateway) {
        this.userGateway = userGateway;
        this.emailGateway = emailGateway;
    }

    @Override
    public void execute(DeleteAccountCommand command) throws JsonProcessingException {
        for(String id : command.ids()){
            User user = userGateway.findUserById(id);
            userGateway.delete(user);
            emailGateway.sendDeleteNotification(user.getEmail());
        }
    }

}
