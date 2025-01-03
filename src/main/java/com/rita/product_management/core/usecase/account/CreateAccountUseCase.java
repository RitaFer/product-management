package com.rita.product_management.core.usecase.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rita.product_management.core.common.util.RandomPasswordGenerator;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.account.command.CreateAccountCommand;
import com.rita.product_management.entrypoint.api.dto.response.AccountResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class CreateAccountUseCase implements UseCase<CreateAccountCommand, AccountResponse> {

    private final UserGateway userGateway;
    private final EmailGateway emailGateway;

    public CreateAccountUseCase(UserGateway userGateway, EmailGateway emailGateway) {
        this.userGateway = userGateway;
        this.emailGateway = emailGateway;
    }

    @Override
    public AccountResponse execute(CreateAccountCommand command) throws JsonProcessingException {
        User user = userGateway
                .save(new User(
                        command.name(),
                        command.email(),
                        command.role()
                ));
        emailGateway.sendCreateAccountNotification(user);
        return mapToAccountResponse(user);
    }

    private AccountResponse mapToAccountResponse(User user) {
        return new AccountResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

}
