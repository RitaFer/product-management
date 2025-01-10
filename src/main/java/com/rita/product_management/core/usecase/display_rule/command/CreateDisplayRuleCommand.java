package com.rita.product_management.core.usecase.display_rule.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateDisplayRuleCommand(
        @NotNull @NotEmpty List<String> hiddenFields
) implements Command {

}
