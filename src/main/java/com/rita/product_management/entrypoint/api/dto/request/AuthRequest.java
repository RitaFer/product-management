package com.rita.product_management.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthRequest (
        @NotNull(message = "cannot be null") String username,
        @NotNull(message = "cannot be null") String password
) {

}
