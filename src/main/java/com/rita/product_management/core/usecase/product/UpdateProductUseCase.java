package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.common.exception.NoChangesException;
import com.rita.product_management.core.domain.AuditLog;
import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.enums.ActionType;
import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.AuditLogGateway;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.product.command.UpdateProductCommand;
import com.rita.product_management.entrypoint.api.dto.response.CategoryProductResponse;
import com.rita.product_management.entrypoint.api.dto.response.ProductResponse;
import com.rita.product_management.entrypoint.api.dto.response.UserProductResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class UpdateProductUseCase implements UseCase<UpdateProductCommand, ProductResponse> {

    private final UserGateway userGateway;
    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;
    private final AuditLogGateway auditLogGateway;

    @Override
    public ProductResponse execute(UpdateProductCommand command) {
        log.info("Executing UpdateProductUseCase for product ID: [{}]", command.id());
        LocalDateTime updatedDate = Instant.now().atZone(ZoneOffset.UTC).toLocalDateTime();

        try {
            Product product = productGateway.findById(command.id());
            log.debug("Product found: [{}]", product.getId());

            UpdateResult updateResult = updateProductFields(product, command);

            if (!updateResult.hasChanges()) {
                log.warn("No fields were updated for product ID: [{}]", command.id());
                throw new NoChangesException("No fields were updated.");
            }

            User updatedBy = getUser();
            product.setUpdatedBy(updatedBy);
            product.setUpdatedAt(updatedDate);

            Product updatedProduct = productGateway.save(product);
            log.debug("Product successfully updated: [{}]", updatedProduct);

            saveAuditLogs(product.getId(), updatedBy, updatedDate, updateResult);

            ProductResponse response = mapProductToProductResponse(updatedProduct);
            log.info("ProductResponse successfully created for product: [{}]", response.id());

            return response;
        } catch (NoChangesException e) {
            log.warn("No changes detected for product ID: [{}]. Message: [{}]", command.id(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred during account update for product ID: [{}]", command.id(), e);
            throw new RuntimeException("Failed to execute UpdateProductUseCase", e);
        }
    }

    private UpdateResult updateProductFields(Product product, UpdateProductCommand command) {
        log.debug("Updating fields for product ID: [{}]", product.getId());
        UpdateResult result = new UpdateResult();

        if (!product.getName().equals(command.name())) {
            result.addChange("name", product.getName(), command.name());
            product.setName(command.name());
        }

        if (!product.getActive().equals(command.active())) {
            result.addChange("active", product.getActive(), command.active());
            product.setActive(command.active());
        }

        if (!product.getSku().equals(command.sku())) {
            result.addChange("sku", product.getSku(), command.sku());
            product.setSku(command.sku());
        }

        Category category = getCategory(command.categoryId());
        if (!product.getCategory().equals(category)) {
            result.addChange("category", product.getCategory().getName(), category.getName());
            product.setCategory(category);
        }

        if (getUserRole() == UserType.ADMIN) {
            if (!product.getCostValue().equals(command.costValue())) {
                result.addChange("costValue", product.getCostValue(), command.costValue());
                product.setCostValue(command.costValue());
            }

            if (!product.getIcms().equals(command.icms())) {
                result.addChange("icms", product.getIcms(), command.icms());
                product.setIcms(command.icms());
            }
        }

        if (!product.getSaleValue().equals(command.saleValue())) {
            result.addChange("saleValue", product.getSaleValue(), command.saleValue());
            product.setSaleValue(command.saleValue());
        }

        if (!product.getQuantityInStock().equals(command.quantityInStock())) {
            result.addChange("quantityInStock", product.getQuantityInStock(), command.quantityInStock());
            product.setQuantityInStock(command.quantityInStock());
        }

        log.debug("Fields updated for product ID: [{}]: [{}]", product.getId(), result.getChangesDescription());
        return result;
    }

    private void saveAuditLogs(String productId, User user, LocalDateTime updatedDate, UpdateResult updateResult) {
        updateResult.getChanges().forEach(change -> {
            AuditLog auditLog = AuditLog.builder()
                    .entityName("Product")
                    .entityId(productId)
                    .action(ActionType.UPDATE)
                    .field(change.field())
                    .oldValue(change.oldValue().toString())
                    .newValue(change.newValue().toString())
                    .modifiedBy(user)
                    .modifiedDate(updatedDate)
                    .build();

            auditLogGateway.save(auditLog);
            log.debug("Audit log registered for product ID: [{}], field: [{}]", productId, change.field());
        });
    }

    @Getter
    private static class UpdateResult {
        private final List<Change> changes = new ArrayList<>();

        public void addChange(String field, Object oldValue, Object newValue) {
            changes.add(new Change(field, oldValue, newValue));
        }

        public boolean hasChanges() {
            return !changes.isEmpty();
        }

        public String getChangesDescription() {
            return changes.stream()
                    .map(change -> change.field() + ": " + change.oldValue() + " -> " + change.newValue())
                    .collect(Collectors.joining(", "));
        }
    }

        private record Change(String field, Object oldValue, Object newValue) {

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

    private Category getCategory(final String id) {
        return categoryGateway.findById(id);
    }

    private ProductResponse mapProductToProductResponse(Product product) {
        log.debug("Mapping Product to ProductResponse for productId: [{}]", product.getId());
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getActive(),
                product.getSku(),
                mapCategoryToCategoryProductResponse(product.getCategory()),
                product.getCostValue(),
                product.getIcms(),
                product.getSaleValue(),
                product.getQuantityInStock(),
                mapUserToUserProductResponse(product.getCreatedBy()),
                product.getCreatedAt(),
                mapUserToUserProductResponse(product.getUpdatedBy()),
                product.getUpdatedAt()
        );
    }

    private UserProductResponse mapUserToUserProductResponse(User user) {
        log.debug("Mapping User to UserProductResponse for userId: [{}]", user.getId());
        return new UserProductResponse(
                user.getId(),
                user.getName()
        );
    }

    private CategoryProductResponse mapCategoryToCategoryProductResponse(Category category) {
        log.debug("Mapping Category to CategoryProductResponse for categoryId: [{}]", category.getId());
        return new CategoryProductResponse(
                category.getId(),
                category.getName()
        );
    }

    private UserType getUserRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username =  principal.toString();
            }
        }

        return userGateway.findUserByUsername(username).getRole();
    }

}
