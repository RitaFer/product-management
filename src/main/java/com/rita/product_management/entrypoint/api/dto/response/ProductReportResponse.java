package com.rita.product_management.entrypoint.api.dto.response;

import java.math.BigDecimal;

public record ProductReportResponse(
        String id,
        String name,
        BigDecimal costValue,
        Long quantityInStock,
        BigDecimal totalCost,
        BigDecimal saleValue,
        BigDecimal totalSaleValue

) {
}
