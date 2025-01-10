package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.domain.User;
import com.rita.product_management.core.domain.enums.ActionType;
import com.rita.product_management.core.gateway.AuditLogGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.product.command.DeleteProductCommand;
import com.rita.product_management.mocks.ProductMockProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.mockito.Mockito.*;

class DeleteProductUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private AuditLogGateway auditLogGateway;

    @InjectMocks
    private DeleteProductUseCase deleteProductUseCase;

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
    void givenValidCommand_whenExecute_thenProductsDeletedAndAuditLogSaved() {
        DeleteProductCommand command = new DeleteProductCommand(List.of("product-1", "product-2"));
        User mockUser = ProductMockProvider.createUser();

        when(userGateway.findUserByUsername("mock-username")).thenReturn(mockUser);

        deleteProductUseCase.execute(command);

        verify(productGateway, times(2)).delete(anyString());
        verify(auditLogGateway, times(2)).save(argThat(auditLog ->
                auditLog.getEntityName().equals("product") &&
                        auditLog.getAction() == ActionType.DELETE
        ));
        verify(userGateway, times(2)).findUserByUsername("mock-username");
    }

    @Test
    void givenInvalidProductId_whenExecute_thenLogErrorAndContinue() {
        DeleteProductCommand command = new DeleteProductCommand(List.of("invalid-product", "product-2"));
        User mockUser = ProductMockProvider.createUser();

        when(userGateway.findUserByUsername("mock-username")).thenReturn(mockUser);
        doThrow(new RuntimeException("Product not found")).when(productGateway).delete("invalid-product");

        deleteProductUseCase.execute(command);

        verify(productGateway, times(1)).delete("invalid-product");
        verify(productGateway, times(1)).delete("product-2");
        verify(auditLogGateway, times(1)).save(any());
        verify(userGateway, times(1)).findUserByUsername("mock-username");
    }

    @Test
    void givenNoProducts_whenExecute_thenNoInteractions() {
        DeleteProductCommand command = new DeleteProductCommand(List.of());

        deleteProductUseCase.execute(command);

        verifyNoInteractions(productGateway, auditLogGateway, userGateway);
    }
}
