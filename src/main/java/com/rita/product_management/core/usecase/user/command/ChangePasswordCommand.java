package com.rita.product_management.core.usecase.user.command;

import jakarta.validation.constraints.NotNull;

public record ChangePasswordCommand(
        @NotNull String token,
        @NotNull String newPassword,
        @NotNull String confirmNewPassword
) {
}
