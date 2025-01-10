package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.domain.enums.FileType;
import com.rita.product_management.mocks.ProductMockProvider;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.usecase.product.command.GetProductReportFileCommand;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerateReportUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @InjectMocks
    private GenerateReportUseCase generateReportUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configura o comportamento padrão do mock
        when(productGateway.findAllWithFilters(any(), any())).thenReturn(ProductMockProvider.createProductPage());
    }

    @Test
    void givenValidCsvCommand_whenExecute_thenReturnCsvResponse() {
        GetProductReportFileCommand command = ProductMockProvider.createReportCommand(FileType.CSV);

        ResponseEntity<byte[]> response = generateReportUseCase.execute(command);

        assertNotNull(response);
        assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
        assertTrue(new String(response.getBody()).contains("id,name,sku"));

        verify(productGateway, times(1)).findAllWithFilters(command.pageable(), command.filter());
    }

    @Test
    void givenValidXlsxCommand_whenExecute_thenReturnXlsxResponse() throws Exception {
        GetProductReportFileCommand command = ProductMockProvider.createReportCommand(FileType.XLSX);

        ResponseEntity<byte[]> response = generateReportUseCase.execute(command);

        assertNotNull(response);
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(response.getBody())) {
            assertNotNull(WorkbookFactory.create(inputStream));
        }

        verify(productGateway, times(1)).findAllWithFilters(command.pageable(), command.filter());
    }

    @Test
    void givenInvalidFileType_whenExecute_thenThrowBusinessException() {
        GetProductReportFileCommand command = ProductMockProvider.createReportCommand(null);

        assertThrows(RuntimeException.class, () -> generateReportUseCase.execute(command));
    }

    @Test
    void givenEmptyProductList_whenExecute_thenReturnEmptyFile() {
        GetProductReportFileCommand command = ProductMockProvider.createReportCommand(FileType.CSV);
        when(productGateway.findAllWithFilters(command.pageable(), command.filter())).thenReturn(Page.empty());

        ResponseEntity<byte[]> response = generateReportUseCase.execute(command);

        assertNotNull(response);
        assertTrue(new String(response.getBody()).contains("id,name,sku"));
        assertFalse(new String(response.getBody()).contains("product-id"));

        verify(productGateway, times(1)).findAllWithFilters(command.pageable(), command.filter());
    }
}
