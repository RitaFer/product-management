package com.rita.product_management.core.usecase.product;

import com.rita.product_management.mocks.ProductMockProvider;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.gateway.DisplayRuleGateway;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.product.command.GetProductListCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductListUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private DisplayRuleGateway displayRuleGateway;

    @InjectMocks
    private GetProductListUseCase getProductListUseCase;

    private static final String MOCK_USERNAME = "mock-username";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(MOCK_USERNAME);

        when(userGateway.findUserByUsername(MOCK_USERNAME)).thenReturn(ProductMockProvider.createUserWithRole(UserType.ADMIN));
    }

    @Test
    void givenAdminRole_whenExecute_thenReturnFullProductDetails() {
        GetProductListCommand command = new GetProductListCommand(Pageable.unpaged(), null);
        Page<Product> mockProducts = new PageImpl<>(List.of(ProductMockProvider.createProduct()));

        when(productGateway.findAllWithFilters(command.pageable(), command.filter())).thenReturn(mockProducts);

        Page<?> result = getProductListUseCase.execute(command);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        verify(userGateway, times(1)).findUserByUsername(MOCK_USERNAME);
        verify(productGateway, times(1)).findAllWithFilters(command.pageable(), command.filter());
        verifyNoInteractions(displayRuleGateway);
    }

    @Test
    void givenNonAdminRole_whenExecute_thenReturnFilteredProductDetails() {
        GetProductListCommand command = new GetProductListCommand(Pageable.unpaged(), null);
        Page<Product> mockProducts = new PageImpl<>(List.of(ProductMockProvider.createProduct()));
        List<String> hiddenFields = List.of("costValue", "icms");

        when(userGateway.findUserByUsername(MOCK_USERNAME)).thenReturn(ProductMockProvider.createUserWithRole(UserType.STOCKIST));
        when(productGateway.findAllWithFilters(command.pageable(), command.filter())).thenReturn(mockProducts);
        when(displayRuleGateway.getHiddenFieldsForRole(UserType.STOCKIST)).thenReturn(hiddenFields);

        Page<?> result = getProductListUseCase.execute(command);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        Map<String, Object> productMap = (Map<String, Object>) result.getContent().getFirst();
        assertFalse(productMap.containsKey("costValue"));
        assertFalse(productMap.containsKey("icms"));

        verify(userGateway, times(1)).findUserByUsername(MOCK_USERNAME);
        verify(productGateway, times(1)).findAllWithFilters(command.pageable(), command.filter());
        verify(displayRuleGateway, times(1)).getHiddenFieldsForRole(UserType.STOCKIST);
    }

    @Test
    void givenNullUserRole_whenExecute_thenThrowRuntimeException() {
        GetProductListCommand command = new GetProductListCommand(Pageable.unpaged(), null);

        when(userGateway.findUserByUsername(MOCK_USERNAME)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> getProductListUseCase.execute(command));
        assertEquals("Failed to execute GetProductListUseCase", exception.getMessage());

        verify(userGateway, times(1)).findUserByUsername(MOCK_USERNAME);
        verifyNoInteractions(productGateway, displayRuleGateway);
    }

    @Test
    void givenNoProducts_whenExecute_thenReturnEmptyPage() {
        GetProductListCommand command = new GetProductListCommand(Pageable.unpaged(), null);

        when(productGateway.findAllWithFilters(command.pageable(), command.filter())).thenReturn(Page.empty());

        Page<?> result = getProductListUseCase.execute(command);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());

        verify(userGateway, times(1)).findUserByUsername(MOCK_USERNAME);
        verify(productGateway, times(1)).findAllWithFilters(command.pageable(), command.filter());
        verifyNoInteractions(displayRuleGateway);
    }
}
