package com.rita.product_management.core.usecase.product.command;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SwitchProductCommand(
        @NotNull List<String> ids,
        @NotNull Boolean isActive
) {
}
