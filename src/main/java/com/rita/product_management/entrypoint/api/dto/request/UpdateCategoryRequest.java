package com.rita.product_management.entrypoint.api.dto.request;

import com.rita.product_management.core.domain.enums.CategoryType;
import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequest(
        @NotNull(message = "cannot be null") String id,
        @NotNull(message = "cannot be null") String name,
        @NotNull(message = "cannot be null") String active,
        @NotNull(message = "cannot be null") CategoryType type
) {

}
