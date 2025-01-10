package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.usecase.product.command.GetProductCommand;
import com.rita.product_management.entrypoint.api.dto.response.ProductResponse;
import com.rita.product_management.mocks.ProductMockProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @InjectMocks
    private GetProductUseCase getProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidCommand_whenExecute_thenReturnProductResponse() {
        GetProductCommand command = new GetProductCommand("product-id");
        Product mockProduct = ProductMockProvider.createProduct();

        when(productGateway.findById(command.id())).thenReturn(mockProduct);

        ProductResponse result = getProductUseCase.execute(command);

        assertNotNull(result);
        assertEquals(mockProduct.getId(), result.id());
        assertEquals(mockProduct.getName(), result.name());
        assertEquals(mockProduct.getActive(), result.active());
        assertEquals(mockProduct.getSku(), result.sku());
        assertEquals(mockProduct.getCategory().getId(), result.category().id());
        assertEquals(mockProduct.getCategory().getName(), result.category().name());
        assertEquals(mockProduct.getCostValue(), result.costValue());
        assertEquals(mockProduct.getIcms(), result.icms());
        assertEquals(mockProduct.getSaleValue(), result.saleValue());
        assertEquals(mockProduct.getQuantityInStock(), result.quantityInStock());
        assertEquals(mockProduct.getCreatedBy().getId(), result.createdBy().id());
        assertEquals(mockProduct.getCreatedBy().getName(), result.createdBy().name());
        assertEquals(mockProduct.getUpdatedBy().getId(), result.updatedBy().id());
        assertEquals(mockProduct.getUpdatedBy().getName(), result.updatedBy().name());

        verify(productGateway, times(1)).findById(command.id());
    }

    @Test
    void givenInvalidProductId_whenExecute_thenThrowException() {
        GetProductCommand command = new GetProductCommand("invalid-product-id");

        when(productGateway.findById(command.id())).thenThrow(new RuntimeException("Product not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> getProductUseCase.execute(command));
        assertEquals("Product not found", exception.getMessage());

        verify(productGateway, times(1)).findById(command.id());
    }
}
