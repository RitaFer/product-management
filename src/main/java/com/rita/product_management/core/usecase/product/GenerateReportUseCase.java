package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.common.exception.BusinessException;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.ProductReportFile;
import com.rita.product_management.core.domain.enums.FileType;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.product.command.GetProductReportFileCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GenerateReportUseCase implements UseCase<GetProductReportFileCommand, ResponseEntity<byte[]>> {

    private final ProductGateway productGateway;

    @Override
    public ResponseEntity<byte[]> execute(GetProductReportFileCommand command) {
        log.info("Executing...");

        try {
            Page<ProductReportFile> products = productGateway.findAllWithFilters(command.pageable(), command.filter()).map(this::mapProduct);

            log.info("Successfully fetched [{}] products.", products.getTotalElements());

            byte[] fileContent;
            String filename = generateDynamicFilename(command.format());

            if (command.format() == FileType.CSV) {
                fileContent = generateCsvReport(products, command.fields());
                return createResponseEntity(fileContent, filename, MediaType.TEXT_PLAIN);
            } else if (command.format() == FileType.XLSX) {
                fileContent = generateXlsxReport(products, command.fields());
                return createResponseEntity(fileContent, filename, MediaType.APPLICATION_OCTET_STREAM);
            } else {
                throw new BusinessException("Invalid report format: " + command.format());
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching product list with pagination: [{}]", command.pageable(), e);
            throw new RuntimeException("Failed to execute GenerateReportUseCase", e);
        }
    }

    private byte[] generateCsvReport(Page<ProductReportFile> data, List<String> fields) {
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append(String.join(",", fields)).append("\n");

        data.getContent().forEach(product -> {
            String line = fields.stream()
                    .map(field -> Optional.ofNullable(product.getFieldValue(field)).map(Object::toString).orElse(""))
                    .collect(Collectors.joining(","));
            csvBuilder.append(line).append("\n");
        });

        return csvBuilder.toString().getBytes();
    }

    private byte[] generateXlsxReport(Page<ProductReportFile> data, List<String> fields) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("products");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < fields.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(fields.get(i));
            }

            int rowIndex = 1;
            for (ProductReportFile product : data.getContent()) {
                Row dataRow = sheet.createRow(rowIndex++);
                for (int j = 0; j < fields.size(); j++) {
                    Cell cell = dataRow.createCell(j);
                    Object value = product.getFieldValue(fields.get(j));
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private ResponseEntity<byte[]> createResponseEntity(byte[] fileContent, String filename, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDispositionFormData("attachment", filename);

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileContent);
    }

    private String generateDynamicFilename(FileType fileType) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String extension = fileType == FileType.CSV ? "csv" : "xlsx";
        return String.format("products-%s.%s", date, extension);
    }

    private ProductReportFile mapProduct(Product product) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return new ProductReportFile(
                product.getId(),
                product.getIsActive(),
                product.getName(),
                product.getActive(),
                product.getSku(),
                product.getCategory().getName(),
                product.getCostValue(),
                product.getIcms(),
                product.getSaleValue(),
                product.getQuantityInStock(),
                product.getCreatedBy().getName(),
                product.getCreatedAt().format(formatter),
                product.getUpdatedBy().getName(),
                product.getUpdatedAt().format(formatter)
        );
    }
}
