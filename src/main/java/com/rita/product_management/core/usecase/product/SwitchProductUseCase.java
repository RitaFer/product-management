package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.domain.AuditLog;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.enums.ActionType;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.AuditLogGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.product.command.SwitchProductCommand;
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
public class SwitchProductUseCase implements UnitUseCase<SwitchProductCommand> {

    private final UserGateway userGateway;
    private final ProductGateway productGateway;
    private final AuditLogGateway auditLogGateway;

    @Override
    public void execute(SwitchProductCommand command) {
        log.info("Executing SwitchProductUseCase for products IDs: [{}], is active status: [{}]", command.ids(), command.isActive());
        LocalDateTime updatedDate = Instant.now().atZone(ZoneOffset.UTC).toLocalDateTime();

        for (String id : command.ids()) {
            try {
                log.debug("Processing switch status for product ID: [{}]", id);

                Product product = productGateway.findById(id);
                log.debug("Product found: [{}]", product);

                Boolean previousStatus = product.getIsActive();

                if(previousStatus.equals(command.isActive())){
                    log.warn("No fields were updated for product ID: "+id);
                    break;
                }

                product.setIsActive(command.isActive());
                product.setUpdatedBy(getUser());
                product.setUpdatedAt(updatedDate);

                productGateway.save(product);
                log.debug("Product status successfully updated to [{}]: [{}]", command.isActive(), product.getId());

                saveAuditLog(product, previousStatus, command.isActive());

            } catch (Exception e) {
                log.error("Unexpected error occurred during status update for product ID: [{}]", id, e);
            }
        }

        log.info("SwitchProductUseCase executed successfully for product IDs: [{}]", command.ids());
    }

    private void saveAuditLog(Product product, Boolean oldValue, Boolean newValue) {
        log.debug("Registering audit log for product ID: [{}]", product.getId());

        AuditLog auditLog = AuditLog.builder()
                .entityName("Product")
                .entityId(product.getId())
                .action(ActionType.UPDATE)
                .field("isActive")
                .oldValue(oldValue.toString())
                .newValue(newValue.toString())
                .modifiedBy(product.getUpdatedBy())
                .modifiedDate(product.getUpdatedAt())
                .build();

        auditLogGateway.save(auditLog);
        log.debug("Audit log registered successfully for product ID: [{}]", product.getId());
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


