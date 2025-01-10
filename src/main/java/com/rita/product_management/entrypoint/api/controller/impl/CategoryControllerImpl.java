package com.rita.product_management.entrypoint.api.controller.impl;


import com.rita.product_management.core.usecase.category.*;
import com.rita.product_management.core.usecase.category.command.*;
import com.rita.product_management.entrypoint.api.controller.CategoryController;
import com.rita.product_management.entrypoint.api.dto.request.CreateCategoryRequest;
import com.rita.product_management.entrypoint.api.dto.request.SwitchCategoryRequest;
import com.rita.product_management.entrypoint.api.dto.request.UpdateCategoryRequest;
import com.rita.product_management.entrypoint.api.dto.response.CategoriesResponse;
import com.rita.product_management.entrypoint.api.dto.response.CategoryResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryControllerImpl  implements CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final GetCategoryListUseCase getCategoryListUseCase;
    private final SwitchCategoryUseCase switchCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;


    @Override
    public ResponseEntity<CategoryResponse> createCategory(@Valid CreateCategoryRequest request) {
        CategoryResponse response = createCategoryUseCase.execute(new CreateCategoryCommand(request.name(), request.active(), request.type()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<CategoryResponse> updateCategory(@Valid UpdateCategoryRequest request) {
        CategoryResponse response = updateCategoryUseCase.execute(new UpdateCategoryCommand(request.id(), request.name(), request.active(), request.type()));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CategoryResponse> findCategory(String id) {
        CategoryResponse response = getCategoryUseCase.execute(new GetCategoryCommand(id));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> listCategories(Pageable pageable) {
        Page<CategoriesResponse> response = getCategoryListUseCase.execute(new GetCategoryListCommand(pageable));
        if (response.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<Void> switchCategory(@Valid SwitchCategoryRequest request) {
        switchCategoryUseCase.execute(new SwitchCategoryCommand(request.ids(), request.isActive()));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteCategory(List<String> ids) {
        deleteCategoryUseCase.execute(new DeleteCategoryCommand(ids));
        return ResponseEntity.ok().build();
    }

}
