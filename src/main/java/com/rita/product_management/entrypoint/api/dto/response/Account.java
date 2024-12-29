package com.rita.product_management.entrypoint.api.dto.response;

import com.rita.product_management.core.domain.enums.UserType;
import jakarta.validation.constraints.NotNull;

public record Account(
        @NotNull String id,
        @NotNull String name,
        @NotNull String email,
        @NotNull UserType role
) {
}
