package com.rita.product_management.core.usecase.account.command;

import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateAccountCommand(
        @NotNull String name,
        @NotNull @Email String email,
        @NotNull UserType role
) implements Command {

}
