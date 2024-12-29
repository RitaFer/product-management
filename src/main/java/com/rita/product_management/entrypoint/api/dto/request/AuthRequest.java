package com.rita.product_management.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthRequest (
        @NotNull String username,
        @NotNull String password
) {
}
