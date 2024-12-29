package com.rita.product_management.entrypoint.api.dto.request;

import com.rita.product_management.core.domain.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateAccount(
        @NotNull String name,
        @NotNull String username,
        @NotNull @Email String email,
        @NotNull UserType role
) {
}
