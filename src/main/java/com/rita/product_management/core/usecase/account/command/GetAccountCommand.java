package com.rita.product_management.core.usecase.account.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

public record GetAccountCommand(
        @NotNull(message = "cannot be null") String id
) implements Command {

}

