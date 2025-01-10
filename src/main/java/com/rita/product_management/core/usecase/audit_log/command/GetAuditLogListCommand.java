package com.rita.product_management.core.usecase.audit_log.command;

import com.rita.product_management.core.usecase.Command;
import com.rita.product_management.entrypoint.api.dto.filters.AuditFilter;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public record GetAuditLogListCommand(
        @NotNull Pageable pageable,
        AuditFilter filter
) implements Command {

}
