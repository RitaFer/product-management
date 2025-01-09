package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.account.command.CreateAccountCommand;
import com.rita.product_management.entrypoint.api.dto.response.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class CreateAccountUseCase implements UseCase<CreateAccountCommand, AccountResponse> {

    private final UserGateway userGateway;
    private final EmailGateway emailGateway;

    @Override
    public AccountResponse execute(CreateAccountCommand command) {
        log.info("Executing CreateAccountUseCase for email: [{}], role: [{}]", command.email(), command.role());

        try {
            User user = userGateway.save(new User(
                    command.name(),
                    command.email(),
                    command.role()
            ));
            log.debug("User created and saved successfully: [{}]", user.getId());

            emailGateway.sendCreateAccountNotification(user);
            log.debug("Create account notification email sent successfully to: [{}]", user.getEmail());

            AccountResponse response = mapToAccountResponse(user);
            log.info("AccountResponse successfully created for user: [{}]", response.id());

            return response;

        } catch (Exception e) {
            log.error("Unexpected error occurred during account creation for email: [{}]", command.email(), e);
            throw new RuntimeException("Failed to execute CreateAccountUseCase", e);
        }
    }

    private AccountResponse mapToAccountResponse(User user) {
        log.debug("Mapping User to AccountResponse for userId: [{}]", user.getId());
        return new AccountResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

}
