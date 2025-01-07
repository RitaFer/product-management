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
        log.debug("Saving category: [{}]", category);
        Category savedCategory = categoryMapper.fromEntityToModel(categoryRepository.save(categoryMapper.fromModelToEntity(category)));
        log.debug("Category saved successfully: [{}]", savedCategory);
        return savedCategory;
    }

    @Override
    public Category findCategoryById(String id) {
        log.debug("Searching for category by id: [{}]", id);
        return categoryRepository.findById(id)
                .map(categoryMapper::fromEntityToModel)
                .orElseThrow(() -> {
                    log.error("Category with id [{}] not found.", id);
                    return new CategoryNotFoundException("Category with id = " + id + ", not found.");
                });
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        log.debug("Fetching all categorys with pagination: [{}]", pageable);
        Page<Category> categorys = categoryRepository.findAll(pageable).map(categoryMapper::fromEntityToModel);
        log.debug("Fetched [{}] categorys", categorys.getTotalElements());
        return categorys;
    }

    @Override
    public void delete(String id) {
        log.debug("Deleting category: [{}]", id);
        Category category = findCategoryById(id);
        categoryRepository.delete(categoryMapper.fromModelToEntity(category));
        log.debug("Category deleted successfully: [{}]", category);
    }
}
