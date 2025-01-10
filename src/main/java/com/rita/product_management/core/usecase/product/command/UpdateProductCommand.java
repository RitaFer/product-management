package com.rita.product_management.core.usecase.product.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateProductCommand(
        @NotNull String id,
        @NotNull String name,
        @NotNull String active,
        @NotNull String sku,
        @NotNull @Positive Double icms,
        @NotNull @Positive BigDecimal costValue,
        @NotNull @Positive BigDecimal saleValue,
        @NotNull @PositiveOrZero Long quantityInStock,
        @NotNull String categoryId
) implements Command {

}
