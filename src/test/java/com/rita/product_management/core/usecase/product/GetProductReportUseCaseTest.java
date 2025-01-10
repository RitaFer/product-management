package com.rita.product_management.core.usecase.product;

import com.rita.product_management.mocks.ProductMockProvider;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.usecase.product.command.GetProductListCommand;
import com.rita.product_management.entrypoint.api.dto.response.ProductReportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductReportUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @InjectMocks
    private GetProductReportUseCase getProductReportUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidCommand_whenExecute_thenReturnProductReportResponsePage() {
        GetProductListCommand command = new GetProductListCommand(Pageable.unpaged(), null);
        Page<Product> mockProducts = new PageImpl<>(List.of(ProductMockProvider.createProduct()));

        when(productGateway.findAllWithFilters(command.pageable(), command.filter())).thenReturn(mockProducts);

        Page<ProductReportResponse> result = getProductReportUseCase.execute(command);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        ProductReportResponse reportResponse = result.getContent().getFirst();
        Product mockProduct = mockProducts.getContent().getFirst();

        assertEquals(mockProduct.getId(), reportResponse.id());
        assertEquals(mockProduct.getName(), reportResponse.name());
        assertEquals(mockProduct.getCostValue(), reportResponse.costValue());
        assertEquals(mockProduct.getQuantityInStock(), reportResponse.quantityInStock());
        assertEquals(mockProduct.getCostValue().multiply(BigDecimal.valueOf(mockProduct.getQuantityInStock())), reportResponse.totalCost());
        assertEquals(mockProduct.getSaleValue(), reportResponse.saleValue());
        assertEquals(mockProduct.getSaleValue().multiply(BigDecimal.valueOf(mockProduct.getQuantityInStock())), reportResponse.totalSaleValue());

        verify(productGateway, times(1)).findAllWithFilters(command.pageable(), command.filter());
    }

    @Test
    void givenEmptyProductList_whenExecute_thenReturnEmptyPage() {
        GetProductListCommand command = new GetProductListCommand(Pageable.unpaged(), null);

        when(productGateway.findAllWithFilters(command.pageable(), command.filter())).thenReturn(Page.empty());

        Page<ProductReportResponse> result = getProductReportUseCase.execute(command);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());

        verify(productGateway, times(1)).findAllWithFilters(command.pageable(), command.filter());
    }

    @Test
    void givenExceptionDuringFetch_whenExecute_thenThrowRuntimeException() {
        GetProductListCommand command = new GetProductListCommand(Pageable.unpaged(), null);

        when(productGateway.findAllWithFilters(command.pageable(), command.filter())).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> getProductReportUseCase.execute(command));
        assertEquals("Failed to execute GetProductReportUseCase", exception.getMessage());

        verify(productGateway, times(1)).findAllWithFilters(command.pageable(), command.filter());
    }
}
