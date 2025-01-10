package com.rita.product_management.entrypoint.api.dto.request;

import com.rita.product_management.core.domain.enums.CategoryType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
        @NotNull(message = "cannot be null") String id,
        @NotNull(message = "cannot be null")
        @Size(min = 1, max = 255)
        String name,
        @NotNull(message = "cannot be null")
        @Size(min = 1, max = 255)
        String active,
        @NotNull(message = "cannot be null") CategoryType type
) {

}
