package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.account.command.GetAccountCommand;
import com.rita.product_management.entrypoint.api.dto.response.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetAccountUseCase implements UseCase<GetAccountCommand, AccountResponse> {

    private final UserGateway userGateway;

    @Override
    public AccountResponse execute(GetAccountCommand command) {
        log.info("Executing GetAccountUseCase: [{}]", command.id());

        User account = userGateway.findUserById(command.id());
        return mapToAccountResponse(account);
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
