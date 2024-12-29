package com.rita.product_management.entrypoint.api.dto.response;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AuthResponse(
        @NotNull String token,
        @NotNull LocalDateTime expiration
) {
}
