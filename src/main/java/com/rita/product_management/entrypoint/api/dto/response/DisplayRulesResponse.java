package com.rita.product_management.entrypoint.api.dto.response;

import jakarta.validation.constraints.NotNull;

public record DisplayRulesResponse(
        @NotNull String id,
        @NotNull Boolean isActive
) {
}
