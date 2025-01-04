package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductGateway {
    Product save(final Product product);
    Page<Product> findAll(final Pageable pageable);
    Product delete(final String id);
}
