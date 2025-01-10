package com.rita.product_management.core.usecase.product;

import com.rita.product_management.mocks.ProductMockProvider;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.gateway.AuditLogGateway;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.product.command.UpdateProductCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateProductUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private AuditLogGateway auditLogGateway;

    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenInvalidProductId_whenExecute_thenThrowRuntimeException() {
        UpdateProductCommand command = ProductMockProvider.createUpdateProductCommand();

        when(productGateway.findById(command.id())).thenThrow(new RuntimeException("Product not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> updateProductUseCase.execute(command));
        assertEquals("Failed to execute UpdateProductUseCase", exception.getMessage());

        verify(productGateway, times(1)).findById(command.id());
        verifyNoInteractions(auditLogGateway);
    }

    @Test
    void givenInvalidCategory_whenExecute_thenThrowRuntimeException() {
        UpdateProductCommand command = ProductMockProvider.createUpdateProductCommand();
        Product mockProduct = ProductMockProvider.createProduct();

        when(productGateway.findById(command.id())).thenReturn(mockProduct);
        when(categoryGateway.findById(command.categoryId())).thenThrow(new RuntimeException("Category not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> updateProductUseCase.execute(command));
        assertEquals("Failed to execute UpdateProductUseCase", exception.getMessage());

        verify(productGateway, times(1)).findById(command.id());
        verify(categoryGateway, times(1)).findById(command.categoryId());
        verifyNoInteractions(auditLogGateway);
    }
}
