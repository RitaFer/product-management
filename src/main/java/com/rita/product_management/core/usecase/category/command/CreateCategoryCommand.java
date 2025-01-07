package com.rita.product_management.core.usecase.category.command;

import com.rita.product_management.core.domain.enums.CategoryType;
import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryCommand(
        @NotNull String name,
        @NotNull String active,
        @NotNull CategoryType type
) implements Command {

}
