package com.rita.product_management.entrypoint.api.dto.response;

import jakarta.validation.constraints.NotNull;

public record AccountsResponse(
        @NotNull String id,
        @NotNull Boolean active,
        @NotNull String name
) {
}
