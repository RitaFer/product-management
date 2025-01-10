package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.common.exception.NoChangesException;
import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.product.command.UpdateProductCommand;
import com.rita.product_management.entrypoint.api.dto.response.CategoryProductResponse;
import com.rita.product_management.entrypoint.api.dto.response.ProductResponse;
import com.rita.product_management.entrypoint.api.dto.response.UserProductResponse;
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
public class UpdateProductUseCase implements UseCase<UpdateProductCommand, ProductResponse> {

    private final UserGateway userGateway;
    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

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

            product.setUpdatedBy(getUser());
            product.setUpdatedAt(updatedDate);

            Product updatedProduct = productGateway.save(product);
            log.info("Product successfully updated: [{}]", updatedProduct);

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
            product.setName(command.name());
            result.addChange("name");
        }

        if (!product.getActive().equals(command.active())) {
            product.setActive(command.active());
            result.addChange("active");
        }

        if (!product.getSku().equals(command.sku())) {
            product.setSku(command.sku());
            result.addChange("sku");
        }

        Category category = categoryGateway.findById(command.categoryId());
        if (!product.getCategory().equals(category)) {
            product.setCategory(category);
            result.addChange("category");
        }

        if(getUserRole() == UserType.ADMIN){
            if (!product.getCostValue().equals(command.costValue())) {
                product.setCostValue(command.costValue());
                result.addChange("costValue");
            }

            if (!product.getIcms().equals(command.icms())) {
                product.setIcms(command.icms());
                result.addChange("icms");
            }
        }

        if (!product.getSaleValue().equals(command.saleValue())) {
            product.setSaleValue(command.saleValue());
            result.addChange("saleValue");
        }

        if (!product.getQuantityInStock().equals(command.quantityInStock())) {
            product.setQuantityInStock(command.quantityInStock());
            result.addChange("quantityInStock");
        }

        log.debug("Fields updated for product ID: [{}]: [{}]", product.getId(), result.getChangesDescription());
        return result;
    }

    private static class UpdateResult {
        private final StringBuilder changesDescription;
        private boolean hasChanges;

        public UpdateResult() {
            this.changesDescription = new StringBuilder("Updated fields: ");
            this.hasChanges = false;
        }

        public void addChange(String field) {
            changesDescription.append(field).append(", ");
            hasChanges = true;
        }

        public boolean hasChanges() {
            return hasChanges;
        }

        public String getChangesDescription() {
            if (hasChanges) {
                changesDescription.setLength(changesDescription.length() - 2);
            }
            return changesDescription.toString();
        }
    }

    private User getUser(){
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

        return userGateway.findUserByUsername(username);
    }

    private Category getCategory(final String id){
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
