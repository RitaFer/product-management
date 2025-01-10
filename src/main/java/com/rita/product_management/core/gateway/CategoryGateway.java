package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryGateway {
    Category save(final Category category);
    Category findById(final String id);
    Page<Category> findAll(final Pageable pageable);
    void delete(final String id);
}
