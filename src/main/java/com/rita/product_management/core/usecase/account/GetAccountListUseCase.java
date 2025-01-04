package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.account.command.GetAccountListCommand;
import com.rita.product_management.entrypoint.api.dto.response.AccountsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetAccountListUseCase implements UseCase<GetAccountListCommand, Page<AccountsResponse>> {

    private final UserGateway userGateway;

    @Override
    public Page<AccountsResponse> execute(GetAccountListCommand command) {
        log.info("Executing GetAccountListUseCase with pagination: [{}]", command.pageable());

        try {
            Page<AccountsResponse> accounts = userGateway.findAll(command.pageable())
                    .map(this::mapToAccountsResponse);
            log.info("Successfully fetched [{}] accounts.", accounts.getTotalElements());
            return accounts;

        } catch (Exception e) {
            log.error("Error occurred while fetching account list with pagination: [{}]", command.pageable(), e);
            throw new RuntimeException("Failed to execute GetAccountListUseCase", e);
        }
    }

    private AccountsResponse mapToAccountsResponse(User account) {
        log.debug("Mapping User to AccountsResponse for userId: [{}]", account.getId());
        return new AccountsResponse(account.getId(), account.getActive(), account.getName());
    }

}
