package com.rita.product_management.core.usecase.product.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DeleteProductCommand(
        @NotNull
        List<String> ids
) implements Command {

}