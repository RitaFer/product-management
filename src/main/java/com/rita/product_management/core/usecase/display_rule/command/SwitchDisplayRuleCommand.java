package com.rita.product_management.core.usecase.display_rule.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SwitchDisplayRuleCommand(
        @NotNull String id,
        @NotNull Boolean isActive
) implements Command {

}
