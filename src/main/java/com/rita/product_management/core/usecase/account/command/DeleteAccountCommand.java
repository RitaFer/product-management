package com.rita.product_management.core.usecase.account.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DeleteAccountCommand(
        @NotNull List<String> ids
) implements Command {
}
