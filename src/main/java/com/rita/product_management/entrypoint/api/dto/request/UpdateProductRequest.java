package com.rita.product_management.entrypoint.api.dto.request;

import com.rita.product_management.core.domain.enums.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotNull(message = "cannot be null") String id,
        @NotNull(message = "cannot be null") @Size(min = 1, max = 255) String name,
        @NotNull(message = "cannot be null") @Size(min = 1, max = 50) String active,
        @NotNull(message = "cannot be null") @Size(min = 1, max = 50) String sku,
        @NotNull(message = "cannot be null") @Positive Double icms,
        @NotNull(message = "cannot be null") @Positive BigDecimal costValue,
        @NotNull(message = "cannot be null") @Positive BigDecimal saleValue,
        @NotNull(message = "cannot be null") @PositiveOrZero Long quantityInStock,
        @NotNull(message = "cannot be null") String categoryId
) {
}
