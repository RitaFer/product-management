package com.rita.product_management.entrypoint.api.controller;


import com.rita.product_management.core.domain.enums.FileType;
import com.rita.product_management.entrypoint.api.dto.filters.ProductFilter;
import com.rita.product_management.entrypoint.api.dto.request.CreateProductRequest;
import com.rita.product_management.entrypoint.api.dto.request.SwitchProductRequest;
import com.rita.product_management.entrypoint.api.dto.request.UpdateProductRequest;
import com.rita.product_management.entrypoint.api.dto.response.ProductReportResponse;
import com.rita.product_management.entrypoint.api.dto.response.ProductResponse;
import com.rita.product_management.entrypoint.api.dto.response.ProductsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products API", description = "Endpoints for products management")
@RequestMapping(path = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface ProductController {

    @PostMapping()
    @Operation(
            summary = "Create Product",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product Created",
                            content = @Content(
                                    schema = @Schema(implementation = ProductResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"name\": \"Dorflex\", " +
                                                    "\"active\": \"Dipirona\", " +
                                                    "\"sku\": \"DOR-123\", " +
                                                    "\"category\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Analgesics\" }, " +
                                                    "\"costValue\": 10.50, " +
                                                    "\"icms\": 18.0, " +
                                                    "\"saleValue\": 15.75, " +
                                                    "\"quantityInStock\": 100, " +
                                                    "\"createdBy\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Admin\" }, " +
                                                    "\"createdAt\": \"2025-01-01T10:00:00\", " +
                                                    "\"updatedBy\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Editor\" }, " +
                                                    "\"updatedAt\": \"2025-01-08T14:30:00\" " +
                                                    "}"
                                    )
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product Request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateProductRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Request",
                                    summary = "Example of a valid request",
                                    value =  "{ " +
                                            "\"name\": \"Dorflex\", " +
                                            "\"active\": \"Dipirona\", " +
                                            "\"sku\": \"DOR-123\", " +
                                            "\"categoryId\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                            "\"costValue\": 10.50, " +
                                            "\"icms\": 18.0, " +
                                            "\"saleValue\": 15.75, " +
                                            "\"quantityInStock\": 100 }"
                            )
                    )
            )
    )
    ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request);

    @PutMapping()
    @Operation(
            summary = "Update Product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product Updated",
                            content = @Content(
                                    schema = @Schema(implementation = ProductResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"name\": \"Dorflex\", " +
                                                    "\"active\": \"Dipirona\", " +
                                                    "\"sku\": \"DOR-123\", " +
                                                    "\"category\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Analgesics\" }, " +
                                                    "\"costValue\": 10.50, " +
                                                    "\"icms\": 18.0, " +
                                                    "\"saleValue\": 15.75, " +
                                                    "\"quantityInStock\": 100, " +
                                                    "\"createdBy\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Admin\" }, " +
                                                    "\"createdAt\": \"2025-01-01T10:00:00\", " +
                                                    "\"updatedBy\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Editor\" }, " +
                                                    "\"updatedAt\": \"2025-01-08T14:30:00\" " +
                                                    "}"
                                    )
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product Request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateProductRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Request",
                                    summary = "Example of a valid request",
                                    value =  "{ " +
                                            "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                            "\"name\": \"Dorflex\", " +
                                            "\"active\": \"Dipirona\", " +
                                            "\"sku\": \"DOR-123\", " +
                                            "\"categoryId\": \"283894f9-1610-49f5-815b-63f3efafb253\"" +
                                            "\"costValue\": 10.50, " +
                                            "\"icms\": 18.0, " +
                                            "\"saleValue\": 15.75, " +
                                            "\"quantityInStock\": 100 }"
                            )
                    )
            )
    )
    ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid UpdateProductRequest request);

    @GetMapping("/{id}")
    @Operation(
            summary = "Find Product",
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
                            description = "Product Found",
                            content = @Content(
                                    schema = @Schema(implementation = ProductResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"name\": \"Dorflex\", " +
                                                    "\"active\": \"Dipirona\", " +
                                                    "\"sku\": \"DOR-123\", " +
                                                    "\"category\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Analgesics\" }, " +
                                                    "\"costValue\": 10.50, " +
                                                    "\"icms\": 18.0, " +
                                                    "\"saleValue\": 15.75, " +
                                                    "\"quantityInStock\": 100, " +
                                                    "\"createdBy\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Admin\" }, " +
                                                    "\"createdAt\": \"2025-01-01T10:00:00\", " +
                                                    "\"updatedBy\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Editor\" }, " +
                                                    "\"updatedAt\": \"2025-01-08T14:30:00\" " +
                                                    "}"
                                    )
                            ))
            }
    )
    ResponseEntity<ProductResponse> findProduct(@PathVariable("id") String id);

    @GetMapping()
    @Operation(
            summary = "List of Products",
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
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filters to apply when generating the report",
                    content = @Content(
                            schema = @Schema(implementation = ProductFilter.class),
                            examples = @ExampleObject(value =
                                    "{ " +
                                            "\"id\": \"12345678-1234-5678-1234-567812345678\", " +
                                            "\"isActive\": \"true\", " +
                                            "\"name\": \"Dorflex\", " +
                                            "\"active\": \"Dipirona\", " +
                                            "\"sku\": \"DOR-123\", " +
                                            "\"category\": \"cat-001\", " +
                                            "\"costValueInitial\": 10.00, " +
                                            "\"costValueFinal\": 50.00, " +
                                            "\"icmsInitial\": 5.0, " +
                                            "\"icmsFinal\": 18.0, " +
                                            "\"saleValueInitial\": 15.00, " +
                                            "\"saleValueFinal\": 60.00, " +
                                            "\"quantityInStockInitial\": 10, " +
                                            "\"quantityInStockFinal\": 200, " +
                                            "\"createdBy\": \"user-001\", " +
                                            "\"createdAtInitial\": \"2025-01-01T00:00:00\", " +
                                            "\"createdAtFinal\": \"2025-01-31T23:59:59\", " +
                                            "\"updatedBy\": \"user-002\", " +
                                            "\"updatedAtInitial\": \"2025-01-15T00:00:00\", " +
                                            "\"updatedAtFinal\": \"2025-01-20T23:59:59\" " +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Products",
                            content = @Content(
                                    schema = @Schema(implementation = ProductsResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"name\": \"Dorflex\", " +
                                                    "\"category\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Analgesics\" }, " +
                                                    "\"quantityInStock\": 100, " +
                                                    "\"createdBy\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Admin\" }, " +
                                                    "\"createdAt\": \"2025-01-01T10:00:00\", " +
                                                    "\"updatedBy\": { \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", \"name\": \"Editor\" }, " +
                                                    "\"updatedAt\": \"2025-01-08T14:30:00\" " +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "List of Products Is Empty",
                            content = @Content(
                                    schema = @Schema()
                            )
                    )
            }
    )
    ResponseEntity<?> listProducts(
            @Parameter(hidden = true)
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestBody(required = false) ProductFilter filter);

    @GetMapping("/report")
    @Operation(
            summary = "Report of Products",
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
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filters to apply when generating the report",
                    content = @Content(
                            schema = @Schema(implementation = ProductFilter.class),
                            examples = @ExampleObject(value =
                                    "{ " +
                                            "\"id\": \"12345678-1234-5678-1234-567812345678\", " +
                                            "\"isActive\": \"true\", " +
                                            "\"name\": \"Dorflex\", " +
                                            "\"active\": \"Dipirona\", " +
                                            "\"sku\": \"DOR-123\", " +
                                            "\"category\": \"cat-001\", " +
                                            "\"costValueInitial\": 10.00, " +
                                            "\"costValueFinal\": 50.00, " +
                                            "\"icmsInitial\": 5.0, " +
                                            "\"icmsFinal\": 18.0, " +
                                            "\"saleValueInitial\": 15.00, " +
                                            "\"saleValueFinal\": 60.00, " +
                                            "\"quantityInStockInitial\": 10, " +
                                            "\"quantityInStockFinal\": 200, " +
                                            "\"createdBy\": \"user-001\", " +
                                            "\"createdAtInitial\": \"2025-01-01T00:00:00\", " +
                                            "\"createdAtFinal\": \"2025-01-31T23:59:59\", " +
                                            "\"updatedBy\": \"user-002\", " +
                                            "\"updatedAtInitial\": \"2025-01-15T00:00:00\", " +
                                            "\"updatedAtFinal\": \"2025-01-20T23:59:59\" " +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Report of Products",
                            content = @Content(
                                    schema = @Schema(implementation = ProductReportResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"12345678-1234-5678-1234-567812345678\", " +
                                                    "\"name\": \"Product A\", " +
                                                    "\"costValue\": 10.50, " +
                                                    "\"quantityInStock\": 100, " +
                                                    "\"totalCost\": 1050.00, " +
                                                    "\"saleValue\": 15.75, " +
                                                    "\"totalSaleValue\": 1575.00 " +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Report of Products Is Empty",
                            content = @Content(
                                    schema = @Schema()
                            )
                    )
            }
    )
    ResponseEntity<Page<ProductReportResponse>> getReport(
            @Parameter(hidden = true)
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestBody(required = false) ProductFilter filter);

    @GetMapping("/report/download")
    @Operation(
            summary = "Download Product Report",
            description = "Generates and downloads a product report file in the specified format (CSV or XLSX) with the selected fields and filters.",
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
                    ),
                    @Parameter(
                            name = "format",
                            description = "The format of the report file to be generated (CSV or XLSX)",
                            required = true,
                            schema = @Schema(implementation = FileType.class, example = "CSV")
                    ),
                    @Parameter(
                            name = "fields",
                            description = "List of fields to include in the report",
                            required = true,
                            example = "\"id\""
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filters to apply when generating the report",
                    content = @Content(
                            schema = @Schema(implementation = ProductFilter.class),
                            examples = @ExampleObject(value =
                                    "{ " +
                                            "\"id\": \"12345678-1234-5678-1234-567812345678\", " +
                                            "\"isActive\": \"true\", " +
                                            "\"name\": \"Dorflex\", " +
                                            "\"active\": \"Dipirona\", " +
                                            "\"sku\": \"DOR-123\", " +
                                            "\"category\": \"cat-001\", " +
                                            "\"costValueInitial\": 10.00, " +
                                            "\"costValueFinal\": 50.00, " +
                                            "\"icmsInitial\": 5.0, " +
                                            "\"icmsFinal\": 18.0, " +
                                            "\"saleValueInitial\": 15.00, " +
                                            "\"saleValueFinal\": 60.00, " +
                                            "\"quantityInStockInitial\": 10, " +
                                            "\"quantityInStockFinal\": 200, " +
                                            "\"createdBy\": \"user-001\", " +
                                            "\"createdAtInitial\": \"2025-01-01T00:00:00\", " +
                                            "\"createdAtFinal\": \"2025-01-31T23:59:59\", " +
                                            "\"updatedBy\": \"user-002\", " +
                                            "\"updatedAtInitial\": \"2025-01-15T00:00:00\", " +
                                            "\"updatedAtFinal\": \"2025-01-20T23:59:59\" " +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product report file generated successfully",
                            content = @Content(
                                    mediaType = "application/octet-stream"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No products found for the given filters",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    ResponseEntity<?> getReportFile(
            @Parameter(hidden = true)
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam FileType format,
            @RequestParam List<String> fields,
            @RequestBody(required = false) ProductFilter filter,
            HttpServletResponse response);


    @PatchMapping()
    @Operation(
            summary = "Switch Products",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Products Switched",
                            content = @Content(
                                    schema = @Schema()
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Switch Products Request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = SwitchProductRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Request",
                                    summary = "Example of a valid request",
                                    value = "{ " +
                                            "\"ids\": [\"12327b98-023a-4b33-9563-308684a61b3d\", \"0ae1e804-df33-47c6-bc2c-4b8671d96f88\"], " +
                                            "\"isActive\": true }"
                            )
                    )
            )
    )
    ResponseEntity<Void> switchProduct(@RequestBody @Valid SwitchProductRequest request);

    @DeleteMapping()
    @Operation(
            summary = "Delete Products",
            parameters = {
                    @Parameter(
                            name = "ids",
                            description = "List of ids for delete",
                            required = true,
                            example =  "\"12327b98-023a-4b33-9563-308684a61b3d\""
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Products Deleted",
                            content = @Content(
                                    schema = @Schema()
                            ))
            }
    )
    ResponseEntity<Void> deleteProduct(@RequestParam @NotEmpty(message = "cannot be empty")
                                        @NotNull(message = "cannot be null") List<String> ids);

}
