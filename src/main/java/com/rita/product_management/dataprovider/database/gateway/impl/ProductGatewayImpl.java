package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.gateway.ProductGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ProductGatewayImpl implements ProductGateway {
    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Product delete(String id) {
        return null;
    }
}
