package com.rita.product_management.core.usecase.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.account.command.SwitchAccountCommand;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class SwitchAccountUseCase implements UnitUseCase<SwitchAccountCommand> {

    private final String NOTIFICATION_MESSAGE = "Your account has a new active status =";
    private final UserGateway userGateway;
    private final EmailGateway emailGateway;

    public SwitchAccountUseCase(UserGateway userGateway, EmailGateway emailGateway) {
        this.userGateway = userGateway;
        this.emailGateway = emailGateway;
    }

    @Override
    public void execute(SwitchAccountCommand command) throws JsonProcessingException {
        for(String id : command.ids()){
            User user = userGateway.findUserById(id);
            user.setActive(command.active());
            userGateway.save(user);
            emailGateway.sendUpdateNotification(user.getEmail(), NOTIFICATION_MESSAGE+command.active());
        }
    }

}
