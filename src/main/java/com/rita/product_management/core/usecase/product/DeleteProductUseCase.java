package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.domain.AuditLog;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.core.domain.enums.ActionType;
import com.rita.product_management.core.gateway.AuditLogGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.product.command.DeleteProductCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class DeleteProductUseCase implements UnitUseCase<DeleteProductCommand> {

    private final UserGateway userGateway;
    private final ProductGateway categoryGateway;
    private final AuditLogGateway auditLogGateway;

    @Override
    public void execute(DeleteProductCommand command) {
        log.info("Executing DeleteProductUseCase for user IDs: [{}]", command.ids());

        for (String id : command.ids()) {
            try {
                log.debug("Processing deletion for user ID: [{}]", id);
                categoryGateway.delete(id);
                saveAuditLog(id);
                log.debug("Product successfully deleted: [{}]", id);
            } catch (Exception e) {
                log.error("Unexpected error occurred during account deletion for user ID: [{}]", id, e);
            }
        }

        log.info("DeleteProductUseCase executed successfully for user IDs: [{}]", command.ids());
    }

    private void saveAuditLog(String id) {
        log.debug("Registering audit log for product: [{}]", id);
        LocalDateTime deletedDate = Instant.now().atZone(ZoneOffset.UTC).toLocalDateTime();

        AuditLog auditLog = AuditLog.builder()
                .entityName("product")
                .entityId(id)
                .action(ActionType.DELETE)
                .field(null)
                .oldValue(null)
                .newValue(null)
                .modifiedBy(getUser())
                .modifiedDate(deletedDate)
                .build();

        auditLogGateway.save(auditLog);
        log.debug("Audit log registered successfully for product: [{}]", id);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        }

        return userGateway.findUserByUsername(username);
    }

}
