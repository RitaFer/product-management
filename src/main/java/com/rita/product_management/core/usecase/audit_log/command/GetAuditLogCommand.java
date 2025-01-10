package com.rita.product_management.core.usecase.audit_log.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;

public record GetAuditLogCommand(
        @NotNull String id
) implements Command {

}

