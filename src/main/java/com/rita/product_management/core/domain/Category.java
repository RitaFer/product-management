package com.rita.product_management.core.domain;

import com.rita.product_management.core.domain.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private String id;
    private Boolean isActive;
    private String name;
    private String active;
    private CategoryType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Category(String name, String active, CategoryType type) {
        log.debug("Creating Category with name: [{}], active: [{}], and type: [{}]", name, active, type);
        this.name = name;
        this.isActive = false;
        this.active = active;
        this.type = type;
        log.debug("Category [{}] created successfully.", this.name);
    }

}
