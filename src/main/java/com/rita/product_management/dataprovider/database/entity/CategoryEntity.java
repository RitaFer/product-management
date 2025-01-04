package com.rita.product_management.dataprovider.database.entity;

import com.rita.product_management.core.domain.enums.CategoryType;
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

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@EntityListeners(AuditingEntityListener.class)
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String active;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

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
