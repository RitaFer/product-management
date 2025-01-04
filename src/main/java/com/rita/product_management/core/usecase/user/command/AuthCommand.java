package com.rita.product_management.core.usecase.user.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthCommand(
        @NotNull String username,
        @NotNull String password
) implements Command {
}
