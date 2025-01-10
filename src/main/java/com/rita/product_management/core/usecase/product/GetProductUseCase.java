package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.product.command.GetProductCommand;
import com.rita.product_management.entrypoint.api.dto.response.CategoryProductResponse;
import com.rita.product_management.entrypoint.api.dto.response.ProductResponse;
import com.rita.product_management.entrypoint.api.dto.response.UserProductResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetProductUseCase implements UseCase<GetProductCommand, ProductResponse> {

    private final ProductGateway productGateway;

    @Override
    public ProductResponse execute(GetProductCommand command) {
        log.info("Executing GetProductUseCase: [{}]", command.id());

        Product product = productGateway.findById(command.id());
        return mapToProductResponse(product);
    }

    private ProductResponse mapToProductResponse(Product product) {
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
