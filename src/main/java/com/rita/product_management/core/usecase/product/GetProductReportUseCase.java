package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.product.command.GetProductListCommand;
import com.rita.product_management.entrypoint.api.dto.response.ProductReportResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetProductReportUseCase implements UseCase<GetProductListCommand, Page<?>> {

    private final ProductGateway productGateway;

    @Override
    public Page<ProductReportResponse> execute(GetProductListCommand command) {
        log.info("Executing...");

        try {
            Page<ProductReportResponse> products = productGateway.findAllWithFilters(command.pageable(), command.filter()).map(this::mapProductsToProductReportResponse);

            log.info("Successfully fetched [{}] products.", products.getTotalElements());
            return products;
        } catch (Exception e) {
            log.error("Error occurred while fetching product list with pagination: [{}]", command.pageable(), e);
            throw new RuntimeException("Failed to execute GetProductReportUseCase", e);
        }
    }

    private ProductReportResponse mapProductsToProductReportResponse(Product product) {
        log.debug("Mapping Product to ProductReportResponse for productId: [{}]", product.getId());
        return new ProductReportResponse(
                product.getId(),
                product.getName(),
                product.getCostValue(),
                product.getQuantityInStock(),
                getTotalCost(product),
                product.getSaleValue(),
                getTotalSale(product)
        );
    }

    private BigDecimal getTotalCost(Product product){
        return product.getCostValue().multiply(BigDecimal.valueOf(product.getQuantityInStock()));
    }

    private BigDecimal getTotalSale(Product product){
        return product.getSaleValue().multiply(BigDecimal.valueOf(product.getQuantityInStock()));
    }
}
