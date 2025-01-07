package com.rita.product_management.core.usecase.category.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SwitchCategoryCommand(
        @NotNull List<String> ids,
        @NotNull Boolean isActive
) implements Command {

}
