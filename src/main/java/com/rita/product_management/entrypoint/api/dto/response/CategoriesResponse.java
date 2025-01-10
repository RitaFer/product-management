package com.rita.product_management.entrypoint.api.dto.response;

import jakarta.validation.constraints.NotNull;

public record CategoriesResponse(
        @NotNull String id,
        @NotNull Boolean isActive,
        @NotNull String name,
        @NotNull String active
) {

}
