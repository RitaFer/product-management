package com.rita.product_management.entrypoint.api.dto.request;

import com.rita.product_management.core.domain.enums.CategoryType;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequest(
        @NotNull String name,
        @NotNull String active,
        @NotNull CategoryType type
) {

}
