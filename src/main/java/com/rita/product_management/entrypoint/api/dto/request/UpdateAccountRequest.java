package com.rita.product_management.entrypoint.api.dto.request;

import com.rita.product_management.core.domain.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateAccountRequest(
        @NotNull String id,
        @NotNull
        @Size(min = 1, max = 255)
        String name,
        @NotNull
        @Email
        String email,
        @NotNull UserType role
) {

}
