package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.enums.ActionType;
import com.rita.product_management.core.gateway.AuditLogGateway;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.product.command.CreateProductCommand;
import com.rita.product_management.mocks.ProductMockProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CreateProductUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private AuditLogGateway auditLogGateway;

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("mock-username");
    }

    @Test
    void givenValidCommand_whenExecute_thenReturnProductResponse() {
        CreateProductCommand command = ProductMockProvider.createProductCommand();
        Product mockProduct = ProductMockProvider.createProduct();

        when(userGateway.findUserByUsername(anyString())).thenReturn(mockProduct.getCreatedBy());
        when(categoryGateway.findById(command.categoryId())).thenReturn(mockProduct.getCategory());
        when(productGateway.save(any(Product.class))).thenReturn(mockProduct);

        var response = createProductUseCase.execute(command);

        assertNotNull(response);
        assertEquals(mockProduct.getId(), response.id());
        assertEquals(mockProduct.getName(), response.name());

        verify(userGateway, times(1)).findUserByUsername(anyString());
        verify(categoryGateway, times(1)).findById(command.categoryId());
        verify(productGateway, times(1)).save(any(Product.class));
        verify(auditLogGateway, times(1)).save(any());
        verify(auditLogGateway, times(1)).save(argThat(auditLog ->
                auditLog.getEntityName().equals("product") &&
                        auditLog.getAction() == ActionType.CREATE
        ));
    }

    @Test
    void givenErrorInSave_whenExecute_thenThrowRuntimeException() {
        CreateProductCommand command = ProductMockProvider.createProductCommand();

        when(userGateway.findUserByUsername(anyString())).thenReturn(ProductMockProvider.createUser());
        when(categoryGateway.findById(command.categoryId())).thenReturn(ProductMockProvider.createCategory());
        when(productGateway.save(any(Product.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> createProductUseCase.execute(command));

        verify(userGateway, times(1)).findUserByUsername(anyString());
        verify(categoryGateway, times(1)).findById(command.categoryId());
        verify(productGateway, times(1)).save(any(Product.class));
        verify(auditLogGateway, never()).save(any());
    }

}