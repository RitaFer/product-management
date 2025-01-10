package com.rita.product_management.entrypoint.api.dto.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilter {

    private String id;
    private String isActive;
    private String name;
    private String active;
    private String sku;
    private String category;
    private BigDecimal costValueInitial;
    private BigDecimal costValueFinal;
    private Double icmsInitial;
    private Double icmsFinal;
    private BigDecimal saleValueInitial;
    private BigDecimal saleValueFinal;
    private Long quantityInStockInitial;
    private Long quantityInStockFinal;
    private String createdBy;
    private LocalDateTime createdAtInitial;
    private LocalDateTime createdAtFinal;
    private String updatedBy;
    private LocalDateTime updatedAtInitial;
    private LocalDateTime updatedAtFinal;

}