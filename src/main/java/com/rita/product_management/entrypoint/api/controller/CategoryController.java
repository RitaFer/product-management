package com.rita.product_management.entrypoint.api.controller;

import com.rita.product_management.entrypoint.api.config.ErrorMessage;
import com.rita.product_management.entrypoint.api.dto.request.CreateCategoryRequest;
import com.rita.product_management.entrypoint.api.dto.request.SwitchCategoryRequest;
import com.rita.product_management.entrypoint.api.dto.request.UpdateCategoryRequest;
import com.rita.product_management.entrypoint.api.dto.response.CategoriesResponse;
import com.rita.product_management.entrypoint.api.dto.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/categories")
public interface CategoryController {

    @Operation(summary = "Create new category")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", description = "Category created", content = @Content(schema = @Schema(implementation = CategoryResponse.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest request);

    @Operation(summary = "Update a category")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Category updated", content = @Content(schema = @Schema(implementation = CategoryResponse.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Valid UpdateCategoryRequest request);

    @Operation(summary = "List of categories")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "List of categories", content = @Content(schema = @Schema(implementation = CategoriesResponse.class)))
    @ApiResponse(responseCode = "204", description = "List of categories is empty", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<?> listCategories(
            @Parameter(description = "Pageable parameters")
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable);

    @Operation(summary = "Switch a category")
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Category switched", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<Void> switchCategory(@RequestBody @Valid SwitchCategoryRequest request);

    @Operation(summary = "Delete a category")
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Category deleted", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<Void> deleteCategory(@RequestBody List<String> ids);

}
