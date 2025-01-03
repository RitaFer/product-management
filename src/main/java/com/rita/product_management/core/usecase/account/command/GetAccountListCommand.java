package com.rita.product_management.core.usecase.account.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public record GetAccountListCommand(
        @NotNull Pageable pageable
) implements Command {

}
