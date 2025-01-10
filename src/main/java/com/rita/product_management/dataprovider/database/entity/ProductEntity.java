package com.rita.product_management.dataprovider.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String active;

    @Column(nullable = false)
    private String sku;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CategoryEntity category;

    @Column(name = "cost_value", nullable = false)
    private BigDecimal costValue;

    @Column(nullable = false)
    private Double icms;

    @Column(name = "sale_value", nullable = false)
    private BigDecimal saleValue;

    @Column(name = "quantity_in_stock", nullable = false)
    private Long quantityInStock;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private UserEntity createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by", nullable = false)
    private UserEntity updatedBy;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
