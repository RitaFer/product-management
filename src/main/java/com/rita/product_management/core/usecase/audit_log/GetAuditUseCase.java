package com.rita.product_management.core.usecase.audit_log;

import com.rita.product_management.core.domain.AuditLog;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.core.gateway.AuditLogGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.audit_log.command.GetAuditLogCommand;
import com.rita.product_management.entrypoint.api.dto.response.AuditLogResponse;
import com.rita.product_management.entrypoint.api.dto.response.UserProductResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetAuditUseCase implements UseCase<GetAuditLogCommand, AuditLogResponse> {

    private final AuditLogGateway auditGateway;

    @Override
    public AuditLogResponse execute(GetAuditLogCommand command) {
        log.info("Executing...");
        try {
            AuditLog audit = auditGateway.findById(command.id());

            log.info("Successfully audit found - {}", audit.getId());
            return mapAuditLogsToAuditLogResponse(audit);
        } catch (Exception e) {
            log.error("Error occurred while fetching audit with id: [{}]", command.id(), e);
            throw new RuntimeException("Failed to execute GetAuditUseCase", e);
        }
    }

    private AuditLogResponse mapAuditLogsToAuditLogResponse(AuditLog audit) {
        log.debug("Mapping AuditLog to AuditLogResponse for auditId: [{}]", audit.getId());
        return new AuditLogResponse(
                audit.getId(),
                audit.getAction(),
                audit.getEntityName(),
                audit.getEntityId(),
                audit.getField(),
                audit.getOldValue(),
                audit.getNewValue(),
                mapUserToUserProductResponse(audit.getModifiedBy()),
                audit.getModifiedDate()
        );
    }

    private UserProductResponse mapUserToUserProductResponse(User user) {
        log.debug("Mapping User to UserProductResponse for userId: [{}]", user.getId());
        return new UserProductResponse(
                user.getId(),
                user.getName()
        );
    }

}
