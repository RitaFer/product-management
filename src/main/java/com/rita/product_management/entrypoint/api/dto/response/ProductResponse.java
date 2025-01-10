package com.rita.product_management.entrypoint.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse (
         String id,
         String name,
         String active,
         String sku,
         CategoryProductResponse category,
         BigDecimal costValue,
         Double icms,
         BigDecimal saleValue,
         Long quantityInStock,
         UserProductResponse createdBy,
         LocalDateTime createdAt,
         UserProductResponse updatedBy,
         LocalDateTime updatedAt
) {
}
