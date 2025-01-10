package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.CategoryNotFoundException;
import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.dataprovider.database.repository.CategoryRepository;
import com.rita.product_management.dataprovider.mapper.CategoryMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryGatewayImpl implements CategoryGateway {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category save(Category category) {
        log.debug("Saving category with details: [{}]", category);
        Category savedCategory = categoryMapper.fromEntityToModel(
                categoryRepository.save(categoryMapper.fromModelToEntity(category))
        );
        log.debug("Category saved successfully with ID: [{}]", savedCategory.getId());
        return savedCategory;
    }

    @Override
    public Category findById(String id) {
        log.debug("Searching for category by ID: [{}]", id);
        return findCategoryOrThrow(id);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        log.debug("Fetching all categories with pagination: [{}]", pageable);
        Page<Category> categories = categoryRepository.findAll(pageable).map(categoryMapper::fromEntityToModel);
        log.debug("Fetched [{}] Categories.", categories.getTotalElements());
        return categories;
    }

    @Override
    public void delete(String id) {
        log.debug("Deleting category with ID: [{}]", id);
        Category category = findCategoryOrThrow(id);
        categoryRepository.delete(categoryMapper.fromModelToEntity(category));
        log.debug("Category [{}] deleted successfully.", id);
    }

    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            log.error("The category ID cannot be null or empty.");
            throw new IllegalArgumentException("The category ID cannot be null or empty.");
        }
    }

    private Category findCategoryOrThrow(String id) {
        validateId(id);
        return categoryRepository.findById(id)
                .map(categoryMapper::fromEntityToModel)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id = " + id + ", not found."));
    }

}
