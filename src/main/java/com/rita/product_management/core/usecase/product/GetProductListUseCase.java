package com.rita.product_management.core.usecase.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.ProductReportFile;
import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.core.gateway.DisplayRuleGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.product.command.GetProductListCommand;
import com.rita.product_management.entrypoint.api.dto.response.CategoryProductResponse;
import com.rita.product_management.entrypoint.api.dto.response.ProductsResponse;
import com.rita.product_management.entrypoint.api.dto.response.UserProductResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetProductListUseCase implements UseCase<GetProductListCommand, Page<?>> {

    private final UserGateway userGateway;
    private final ProductGateway productGateway;
    private final DisplayRuleGateway displayRuleGateway;

    @Override
    public Page<?> execute(GetProductListCommand command) {
        log.info("Executing GetProductListUseCase with pagination: [{}]", command.pageable());

        try {
            UserType role = getUserRole();
            Page<Product> products = productGateway.findAllWithFilters(command.pageable(), command.filter());
            Page<?> result;

            if(role.equals(UserType.ADMIN)){
                result = products.map(this::mapProductsToProductResponse);
            } else {
                List<String> hiddenFields = displayRuleGateway.getHiddenFieldsForRole(role);
                result = products.map(this::mapProduct)
                        .map(product -> {
                            ObjectMapper mapper = new ObjectMapper();
                            Map<String, Object> productMap = mapper.convertValue(product, Map.class);
                            hiddenFields.forEach(productMap::remove);
                            return productMap;
                        });
            }

            log.info("Successfully fetched [{}] products.", result.getTotalElements());
            return result;
        } catch (Exception e) {
            log.error("Error occurred while fetching product list with pagination: [{}]", command.pageable(), e);
            throw new RuntimeException("Failed to execute GetProductListUseCase", e);
        }
    }

    private ProductsResponse mapProductsToProductResponse(Product product) {
        log.debug("Mapping Product to ProductResponse for productId: [{}]", product.getId());
        return new ProductsResponse(
                product.getId(),
                product.getIsActive(),
                product.getName(),
                mapCategoryToCategoryProductResponse(product.getCategory()),
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

    private ProductReportFile mapProduct(Product product) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return new ProductReportFile(
                product.getId(),
                product.getIsActive(),
                product.getName(),
                product.getActive(),
                product.getSku(),
                product.getCategory().getName(),
                product.getCostValue(),
                product.getIcms(),
                product.getSaleValue(),
                product.getQuantityInStock(),
                product.getCreatedBy().getName(),
                product.getCreatedAt().format(formatter),
                product.getUpdatedBy().getName(),
                product.getUpdatedAt().format(formatter)
        );
    }

}
