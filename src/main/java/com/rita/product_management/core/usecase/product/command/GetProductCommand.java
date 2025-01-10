package com.rita.product_management.core.usecase.product.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

public record GetProductCommand(
        @NotNull String id
) implements Command {

}
