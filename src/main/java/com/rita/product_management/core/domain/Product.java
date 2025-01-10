package com.rita.product_management.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;
    private Boolean isActive;
    private String name;
    private String active;
    private String sku;
    private Category category;
    private BigDecimal costValue;
    private Double icms;
    private BigDecimal saleValue;
    private Long quantityInStock;
    private User createdBy;
    private LocalDateTime createdAt;
    private User updatedBy;
    private LocalDateTime updatedAt;

}
