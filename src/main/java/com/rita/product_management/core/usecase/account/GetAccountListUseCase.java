package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.account.command.GetAccountListCommand;
import com.rita.product_management.entrypoint.api.dto.response.AccountsResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class GetAccountListUseCase implements UseCase<GetAccountListCommand, Page<AccountsResponse>> {

    private final UserGateway userGateway;

    public GetAccountListUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public Page<AccountsResponse> execute(GetAccountListCommand command) {
        return userGateway.findAll(command.pageable()).map(this::mapToAccountsResponse);
    }

    private AccountsResponse mapToAccountsResponse(User account){
        return new AccountsResponse(account.getId(), account.isActive(), account.getName());
    }

}
