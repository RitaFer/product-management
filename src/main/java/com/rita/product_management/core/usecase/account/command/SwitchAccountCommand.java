package com.rita.product_management.core.usecase.account.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SwitchAccountCommand(
        @NotNull List<String> ids,
        @NotNull Boolean active
) implements Command {

}
