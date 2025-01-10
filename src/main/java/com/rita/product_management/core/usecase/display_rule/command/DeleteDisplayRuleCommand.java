package com.rita.product_management.core.usecase.display_rule.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DeleteDisplayRuleCommand(
        @NotNull List<String> ids
) implements Command {

}
