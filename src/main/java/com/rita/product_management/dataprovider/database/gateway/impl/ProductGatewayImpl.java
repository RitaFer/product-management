package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.ProductNotFoundException;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.dataprovider.database.entity.ProductEntity;
import com.rita.product_management.dataprovider.database.repository.ProductRepository;
import com.rita.product_management.dataprovider.database.specification.ProductSpecification;
import com.rita.product_management.dataprovider.mapper.ProductMapper;
import com.rita.product_management.entrypoint.api.dto.filters.ProductFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ProductGatewayImpl implements ProductGateway {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductSpecification productSpecification;

    @Override
    public Product save(Product product) {
        log.debug("Saving product with details: [{}]", product);
        Product savedProduct = productMapper.fromEntityToModel(
                productRepository.save(productMapper.fromModelToEntity(product))
        );
        log.debug("Product saved successfully with ID: [{}]", savedProduct.getId());
        return savedProduct;
    }

    @Override
    public Product findById(String id) {
        log.debug("Searching for product by ID: [{}]", id);
        return findProductOrThrow(id);
    }

    @Override
    public Page<Product> findAllWithFilters(Pageable pageable, ProductFilter filter) {
        log.debug("Fetching all products with pagination: [{}]", pageable);
        if(filter == null){
            filter = new ProductFilter();
        }
        Specification<ProductEntity> specification = productSpecification.getFilter(filter);
        Page<Product> products = productRepository.findAll(specification, pageable).map(productMapper::fromEntityToModel);
        log.debug("Fetched [{}] products.", products.getTotalElements());
        return products;
    }

    @Override
    public void delete(String id) {
        log.debug("Deleting product with ID: [{}]", id);
        Product product = findProductOrThrow(id);
        productRepository.delete(productMapper.fromModelToEntity(product));
        log.debug("Product [{}] deleted successfully.", id);
    }

    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            log.error("The product ID cannot be null or empty.");
            throw new IllegalArgumentException("The product ID cannot be null or empty.");
        }
    }

    private Product findProductOrThrow(String id) {
        validateId(id);
        return productRepository.findById(id)
                .map(productMapper::fromEntityToModel)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

}
