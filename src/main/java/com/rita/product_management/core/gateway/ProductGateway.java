package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.Product;
import com.rita.product_management.entrypoint.api.dto.filters.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductGateway {
    Product save(final Product product);
    Product findById(final String id);
    Page<Product> findAllWithFilters(final Pageable pageable, final ProductFilter filter);
    void delete(final String id);
}
