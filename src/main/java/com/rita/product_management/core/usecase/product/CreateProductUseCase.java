package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.product.command.CreateProductCommand;
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
public class CreateProductUseCase implements UseCase<CreateProductCommand, ProductResponse> {

    private final UserGateway userGateway;
    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

    @Override
    public ProductResponse execute(CreateProductCommand command) {
        log.info("Executing CreateProductUseCase for name: [{}]", command.name());

        try {
            Product product = productGateway.save(mapCommandToProduct(command));
            log.debug("Product created and saved successfully: [{}]", product.getId());

            ProductResponse response = mapProductToProductResponse(product);
            log.info("ProductResponse successfully created for product: [{}]", response.id());

            return response;

        } catch (Exception e) {
            log.error("Unexpected error occurred during account creation for name: [{}]", command.name(), e);
            throw new RuntimeException("Failed to execute CreateProductUseCase", e);
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

    private Product mapCommandToProduct(CreateProductCommand command) {
        log.debug("Mapping Product to ProductResponse for product name: [{}]", command.name());
        User createdBy = getUser();
        Category category = getCategory(command.categoryId());
        LocalDateTime createdDate = Instant.now().atZone(ZoneOffset.UTC).toLocalDateTime();
        return Product
                .builder()
                .isActive(false)
                .name(command.name())
                .active(command.active())
                .sku(command.sku())
                .category(category)
                .costValue(command.costValue())
                .icms(command.icms())
                .saleValue(command.saleValue())
                .quantityInStock(command.quantityInStock())
                .createdBy(createdBy)
                .createdAt(createdDate)
                .updatedBy(createdBy)
                .updatedAt(createdDate)
                .build();
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

}
