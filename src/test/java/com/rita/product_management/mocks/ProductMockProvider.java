package com.rita.product_management.mocks;

import com.rita.product_management.core.domain.AuditLog;
import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.core.domain.enums.ActionType;
import com.rita.product_management.core.domain.enums.FileType;
import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.usecase.product.command.CreateProductCommand;
import com.rita.product_management.core.usecase.product.command.GetProductReportFileCommand;
import com.rita.product_management.core.usecase.product.command.UpdateProductCommand;
import com.rita.product_management.entrypoint.api.dto.filters.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductMockProvider {
    public static CreateProductCommand createProductCommand() {
        return new CreateProductCommand(
                "Product Name",
                "active",
                "SKU123",
                10.0,
                BigDecimal.valueOf(5.0),
                BigDecimal.valueOf(15.0),
                100L,
                "category-id"
        );
    }

    public static Product createProduct() {
        return Product.builder()
                .id("product-id")
                .isActive(false)
                .name("Product Name")
                .active("active")
                .sku("SKU123")
                .category(createCategory())
                .costValue(BigDecimal.valueOf(10.0))
                .icms(5.0)
                .saleValue(BigDecimal.valueOf(15.0))
                .quantityInStock(100L)
                .createdBy(createUser())
                .createdAt(LocalDateTime.now())
                .updatedBy(createUser())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static User createUser() {
        return User.builder()
                .id("user-id")
                .name("User Name")
                .build();
    }

    public static Category createCategory() {
        return Category.builder()
                .id("category-id")
                .name("Category Name")
                .build();
    }

    public static AuditLog createAuditLog() {
        return AuditLog.builder()
                .entityName("product")
                .entityId("product-id")
                .action(ActionType.CREATE)
                .newValue("Product as String")
                .modifiedBy(createUser())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    public static Page<Product> createProductPage() {
        return new PageImpl<>(List.of(createProduct(), createProduct()));
    }

    public static GetProductReportFileCommand createReportCommand(FileType fileType) {
        return new GetProductReportFileCommand(
                Pageable.unpaged(),
                new ProductFilter(),
                List.of("id", "name", "sku"),
                fileType
        );
    }

    public static User createUserWithRole(UserType role) {
        return User.builder()
                .id("user-id")
                .name("User Name")
                .role(role)
                .build();
    }

    public static UpdateProductCommand createUpdateProductCommand() {
        return new UpdateProductCommand(
                "product-id",
                "Updated Product Name",
                "active",
                "UpdatedSKU123",
                20.0,
                BigDecimal.valueOf(5.0),
                BigDecimal.valueOf(25.0),
                200L,
                "updated-category-id"
        );
    }
}
