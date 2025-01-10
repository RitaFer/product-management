package com.rita.product_management.core.usecase.product;

import com.rita.product_management.mocks.ProductMockProvider;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.core.gateway.AuditLogGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.product.command.SwitchProductCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class SwitchProductUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private AuditLogGateway auditLogGateway;

    @InjectMocks
    private SwitchProductUseCase switchProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidCommand_whenExecute_thenSwitchStatusAndSaveAuditLog() {
        SwitchProductCommand command = new SwitchProductCommand(List.of("product-1", "product-2"), true);
        Product mockProduct = ProductMockProvider.createProduct();
        mockProduct.setIsActive(false);
        User mockUser = ProductMockProvider.createUser();

        when(userGateway.findUserByUsername(anyString())).thenReturn(mockUser);
        when(productGateway.findById("product-1")).thenReturn(mockProduct);
        when(productGateway.findById("product-2")).thenReturn(mockProduct);

        switchProductUseCase.execute(command);

        verify(productGateway, times(1)).save(any(Product.class));
        verify(auditLogGateway, times(1)).save(any());
    }

    @Test
    void givenCommandWithNoStatusChange_whenExecute_thenDoNotSaveAuditLog() {
        SwitchProductCommand command = new SwitchProductCommand(List.of("product-1"), true);
        Product mockProduct = ProductMockProvider.createProduct();
        mockProduct.setIsActive(true);

        when(productGateway.findById("product-1")).thenReturn(mockProduct);

        switchProductUseCase.execute(command);

        verify(productGateway, never()).save(any());
        verify(auditLogGateway, never()).save(any());
    }

    @Test
    void givenInvalidProductId_whenExecute_thenLogErrorAndContinue() {
        SwitchProductCommand command = new SwitchProductCommand(List.of("invalid-product"), true);

        when(productGateway.findById("invalid-product")).thenThrow(new RuntimeException("Product not found"));

        switchProductUseCase.execute(command);

        verify(productGateway, times(1)).findById("invalid-product");
        verifyNoInteractions(auditLogGateway);
    }

    @Test
    void givenEmptyCommandList_whenExecute_thenDoNothing() {
        SwitchProductCommand command = new SwitchProductCommand(List.of(), true);

        switchProductUseCase.execute(command);

        verifyNoInteractions(productGateway, auditLogGateway, userGateway);
    }
}
