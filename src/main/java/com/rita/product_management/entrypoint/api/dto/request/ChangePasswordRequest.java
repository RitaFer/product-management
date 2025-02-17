package com.rita.product_management.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
        @NotNull(message = "cannot be null")
        @Pattern(regexp = "^[A-Z0-9]{6}$", message = "The token must have 6 letters or numbers, without especial characters.")
        String token,
        @NotNull(message = "cannot be null") String newPassword,
        @NotNull(message = "cannot be null") String confirmNewPassword
) {

}
