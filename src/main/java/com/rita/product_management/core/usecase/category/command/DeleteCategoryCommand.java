package com.rita.product_management.core.usecase.category.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DeleteCategoryCommand(
        @NotNull List<String> ids
) implements Command {

}
