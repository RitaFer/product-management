package com.rita.product_management.entrypoint.api.dto.filters;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductFilter(
        String id,
        String isActive,
        String name,
        String active,
        String sku,
        String category,
        BigDecimal costValueInitial,
        BigDecimal costValueFinal,
        Double icmsInitial,
        Double icmsFinal,
        BigDecimal saleValueInitial,
        BigDecimal saleValueFinal,
        Long quantityInStockInitial,
        Long quantityInStockFinal,
        String createdBy,
        LocalDateTime createdAtInitial,
        LocalDateTime createdAtFinal,
        String updatedBy,
        LocalDateTime updatedAtInitial,
        LocalDateTime updatedAtFinal
) {
}
