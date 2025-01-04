package com.rita.product_management.dataprovider.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
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

    @Column(nullable = false)
    private CategoryEntity category;

    @Column(name = "cost_value", nullable = false)
    private BigDecimal costValue;

    @Column(nullable = false)
    private Double icms;

    @Column(name = "sale_value", nullable = false)
    private BigDecimal saleValue;

    @Column(name = "quantity_in_stock", nullable = false)
    private Long quantityInStock;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private UserEntity createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    private UserEntity updatedBy;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
