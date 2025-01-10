package com.rita.product_management.core.usecase.display_rule.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public record GetDisplayRuleListCommand(
        @NotNull Pageable pageable
) implements Command {

}
