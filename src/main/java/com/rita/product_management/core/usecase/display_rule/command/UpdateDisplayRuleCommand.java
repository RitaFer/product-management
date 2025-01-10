package com.rita.product_management.core.usecase.display_rule.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateDisplayRuleCommand(
        @NotNull String id,
        @NotNull List<String> hiddenFields
) implements Command {

}
