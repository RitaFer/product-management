package com.rita.product_management.entrypoint.api.dto.response;

import java.time.LocalDateTime;

public record ProductsResponse(
        String id,
        Boolean isActive,
        String name,
        CategoryProductResponse category,
        Long quantityInStock,
        UserProductResponse createdBy,
        LocalDateTime createdAt,
        UserProductResponse updatedBy,
        LocalDateTime updatedAt
) {
}
