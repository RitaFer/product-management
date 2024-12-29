package com.rita.product_management.core.usecase.user.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

public record SendTokenCommand(
        @NotNull String username
) implements Command {
}

