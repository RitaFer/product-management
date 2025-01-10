package com.rita.product_management.entrypoint.api.controller.impl;

import com.rita.product_management.core.domain.enums.FileType;
import com.rita.product_management.core.usecase.product.*;
import com.rita.product_management.core.usecase.product.command.*;
import com.rita.product_management.entrypoint.api.controller.ProductController;
import com.rita.product_management.entrypoint.api.dto.filters.ProductFilter;
import com.rita.product_management.entrypoint.api.dto.request.CreateProductRequest;
import com.rita.product_management.entrypoint.api.dto.request.SwitchProductRequest;
import com.rita.product_management.entrypoint.api.dto.request.UpdateProductRequest;
import com.rita.product_management.entrypoint.api.dto.response.ProductReportResponse;
import com.rita.product_management.entrypoint.api.dto.response.ProductResponse;
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
public class ProductControllerImpl implements ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final GenerateReportUseCase generateReportUseCase;
    private final GetProductReportUseCase getProductReportUseCase;
    private final GetProductListUseCase getProductListUseCase;
    private final SwitchProductUseCase switchProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @Override
    public ResponseEntity<ProductResponse> createProduct(@Valid CreateProductRequest request) {
        ProductResponse response = createProductUseCase.execute(mapRequestToCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(@Valid UpdateProductRequest request) {
        ProductResponse response = updateProductUseCase.execute(mapRequestToCommand(request));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ProductResponse> findProduct(String id) {
        ProductResponse response = getProductUseCase.execute(new GetProductCommand(id));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> listProducts(Pageable pageable, ProductFilter filter) {
        Page<?> response = getProductListUseCase.execute(new GetProductListCommand(pageable, filter));
        if (response.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<Page<ProductReportResponse>> getReport(Pageable pageable, ProductFilter filter) {
        Page<ProductReportResponse> response = getProductReportUseCase.execute(new GetProductListCommand(pageable, filter));
        if (response.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<byte[]> getReportFile(Pageable pageable, FileType format, List<String> fields, ProductFilter filter) {
        return generateReportUseCase.execute(new GetProductReportFileCommand(pageable, filter, fields, format));
    }

    @Override
    public ResponseEntity<Void> switchProduct(@Valid SwitchProductRequest request) {
        switchProductUseCase.execute(new SwitchProductCommand(request.ids(), request.isActive()));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteProduct(List<String> ids) {
        deleteProductUseCase.execute(new DeleteProductCommand(ids));
        return ResponseEntity.ok().build();
    }

    private CreateProductCommand mapRequestToCommand(final CreateProductRequest request){
        return new CreateProductCommand(
                request.name(),
                request.active(),
                request.sku(),
                request.icms(),
                request.costValue(),
                request.saleValue(),
                request.quantityInStock(),
                request.categoryId());
    }

    private UpdateProductCommand mapRequestToCommand(final UpdateProductRequest request){
        return new UpdateProductCommand(
                request.id(),
                request.name(),
                request.active(),
                request.sku(),
                request.icms(),
                request.costValue(),
                request.saleValue(),
                request.quantityInStock(),
                request.categoryId());
    }

}
