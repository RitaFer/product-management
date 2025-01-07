package com.rita.product_management.entrypoint.api.dto.response;

import com.rita.product_management.core.domain.enums.CategoryType;
import jakarta.validation.constraints.NotNull;

public record CategoryResponse(
        @NotNull String id,
        @NotNull String name,
        @NotNull String active,
        @NotNull CategoryType type
) {

}
