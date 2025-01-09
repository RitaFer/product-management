package com.rita.product_management.core.usecase.category.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

public record GetCategoryCommand(
        @NotNull(message = "cannot be null") String id
) implements Command {

}
