package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.gateway.CategoryGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryGatewayImpl implements CategoryGateway {
    @Override
    public Category save(Category category) {
        return null;
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Category delete(String id) {
        return null;
    }
}
