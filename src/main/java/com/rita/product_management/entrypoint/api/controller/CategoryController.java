package com.rita.product_management.entrypoint.api.controller;

import com.rita.product_management.entrypoint.api.dto.request.CreateCategoryRequest;
import com.rita.product_management.entrypoint.api.dto.request.SwitchCategoryRequest;
import com.rita.product_management.entrypoint.api.dto.request.UpdateCategoryRequest;
import com.rita.product_management.entrypoint.api.dto.response.CategoriesResponse;
import com.rita.product_management.entrypoint.api.dto.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categories API", description = "Endpoints for categories management")
@RequestMapping(path = "/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface CategoryController {

    @PostMapping()
    @Operation(
            summary = "Create Category",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Category Created",
                            content = @Content(
                                    schema = @Schema(implementation = CategoryResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"name\": \"Dorflex\", " +
                                                    "\"active\": \"Dipirona\", " +
                                                    "\"type\": \"NORMAL\" }"
                                    )
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category Request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateCategoryRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Request",
                                    summary = "Example of a valid request",
                                    value = "{ " +
                                            "\"name\": \"Dorflex\", " +
                                            "\"active\": \"Dipirona\", " +
                                            "\"type\": \"NORMAL\" }"
                            )
                    )
            )
    )
    ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest request);

    @PutMapping()
    @Operation(
            summary = "Update Category",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category Updated",
                            content = @Content(
                                    schema = @Schema(implementation = CategoryResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"name\": \"Dorflex\", " +
                                                    "\"active\": \"Dipirona\", " +
                                                    "\"type\": \"NORMAL\" }"
                                    )
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category Request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateCategoryRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Request",
                                    summary = "Example of a valid request",
                                    value = "{ " +
                                            "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                            "\"name\": \"Dorflex\", " +
                                            "\"active\": \"Dipirona\", " +
                                            "\"type\": \"NORMAL\" }"
                            )
                    )
            )
    )
    ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Valid UpdateCategoryRequest request);

    @GetMapping("/{id}")
    @Operation(
            summary = "Find Category",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Search id",
                            required = true,
                            example = "12327b98-023a-4b33-9563-308684a61b3d"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category Found",
                            content = @Content(
                                    schema = @Schema(implementation = CategoryResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"name\": \"Dorflex\", " +
                                                    "\"active\": \"Dipirona\", " +
                                                    "\"type\": \"NORMAL\" }"
                                    )
                            ))
            }
    )
    ResponseEntity<CategoryResponse> findCategory(@PathVariable("id") String id);

    @GetMapping()
    @Operation(
            summary = "List of Categories",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "The page number (zero-based) to retrieve",
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "The number of elements per page",
                            example = "20"
                    ),
                    @Parameter(
                            name = "sort",
                            description = "Sorting criteria in the format: property(,asc | desc). Default is name,asc",
                            example = "name,asc"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Categories",
                            content = @Content(
                                    schema = @Schema(implementation = CategoriesResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"isActive\": true, " +
                                                    "\"name\": \"Dorflex\", " +
                                                    "\"active\": \"Dipirona\", " +
                                                    "\"type\": \"NORMAL\" }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "List of Categories Is Empty",
                            content = @Content(
                                    schema = @Schema()
                            )
                    )
            }
    )
    ResponseEntity<?> listCategories(
            @Parameter(hidden = true)
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable);

    @PatchMapping()
    @Operation(
            summary = "Switch Categories",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categories Switched",
                            content = @Content(
                                    schema = @Schema()
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Switch Categories Request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = SwitchCategoryRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Request",
                                    summary = "Example of a valid request",
                                    value = "{ " +
                                            "\"ids\": [\"12327b98-023a-4b33-9563-308684a61b3d\", \"0ae1e804-df33-47c6-bc2c-4b8671d96f88\"]" +
                                            "\"isActive\": true }"
                            )
                    )
            )
    )
    ResponseEntity<Void> switchCategory(@RequestBody @Valid SwitchCategoryRequest request);

    @DeleteMapping()
    @Operation(
            summary = "Delete Categories",
            parameters = {
                    @Parameter(
                            name = "ids",
                            description = "List of ids for delete",
                            required = true,
                            example =  "[\"12327b98-023a-4b33-9563-308684a61b3d\"]"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categories Deleted",
                            content = @Content(
                                    schema = @Schema()
                            ))
            }
    )
    ResponseEntity<Void> deleteCategory(@RequestParam @NotEmpty(message = "cannot be empty")
                                        @NotNull(message = "cannot be null") List<String> ids);

}
