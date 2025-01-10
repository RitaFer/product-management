package com.rita.product_management.core.usecase.audit_log;


import com.rita.product_management.core.domain.AuditLog;
import com.rita.product_management.core.gateway.AuditLogGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.audit_log.command.GetAuditLogListCommand;
import com.rita.product_management.entrypoint.api.dto.response.AuditLogsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetAuditListUseCase implements UseCase<GetAuditLogListCommand, Page<AuditLogsResponse>> {

    private final AuditLogGateway auditGateway;

    @Override
    public Page<AuditLogsResponse> execute(GetAuditLogListCommand command) {
        log.info("Executing...");
        try {
            Page<AuditLogsResponse> audits = auditGateway.findAllWithFilters(command.pageable(), command.filter()).map(this::mapAuditLogsToAuditLogResponse);

            log.info("Successfully fetched [{}] audits.", audits.getTotalElements());
            return audits;
        } catch (Exception e) {
            log.error("Error occurred while fetching audit list with pagination: [{}]", command.pageable(), e);
            throw new RuntimeException("Failed to execute GetAuditLogUseCase", e);
        }
    }

    private AuditLogsResponse mapAuditLogsToAuditLogResponse(AuditLog audit) {
        log.debug("Mapping AuditLog to AuditLogResponse for auditId: [{}]", audit.getId());
        return new AuditLogsResponse(
                audit.getId(),
                audit.getAction(),
                audit.getEntityName(),
                audit.getEntityId(),
                audit.getField(),
                audit.getModifiedDate()
        );
    }

}
