package com.rita.product_management.core.usecase.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rita.product_management.core.domain.Product;
import com.rita.product_management.core.domain.enums.FileType;
import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.product.command.GetProductReportFileCommand;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GenerateReportUseCase implements UnitUseCase<GetProductReportFileCommand> {

    private final ProductGateway productGateway;

    @Override
    public void execute(GetProductReportFileCommand command) {
        log.info("Executing...");

        try {
            Page<List<Map<String, Object>>> products = productGateway.findAllWithFilters(command.pageable(), command.filter())
                    .map(product -> applySelectedFieldsToList(transformProductToObjectList(product), command.fields()));

            log.info("Successfully fetched [{}] products.", products.getTotalElements());

            try {
                if (command.format() == FileType.CSV) {
                    generateCsvReport(products, command.fields(), command.servletResponse());
                } else if (command.format() == FileType.XLSX) {
                    generateXlsxReport(products, command.fields(), command.servletResponse());
                } else {
                    throw new IllegalArgumentException("Invalid report format: " + command.format());
                }
            } catch (IOException e) {
                throw new RuntimeException("Error generating report", e);
            }

        } catch (Exception e) {
            log.error("Error occurred while fetching product list with pagination: [{}]", command.pageable(), e);
            throw new RuntimeException("Failed to execute GetProductListUseCase", e);
        }
    }

    private List<Map<String, Object>> applySelectedFieldsToList(List<?> objects, List<String> selectedFields) {
        ObjectMapper mapper = new ObjectMapper();
        return objects.stream()
                .map(object -> {
                    Map<String, Object> objectMap = mapper.convertValue(object, Map.class);
                    return objectMap.entrySet().stream()
                            .filter(entry -> selectedFields.contains(entry.getKey()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                })
                .toList();
    }


    public List<Map<String, Object>> transformProductToObjectList(Product product) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> productMap = mapper.convertValue(product, Map.class);

        return productMap.entrySet().stream()
                .map(entry -> Map.of("field", entry.getKey(), "value", entry.getValue()))
                .toList();
    }

    private void generateCsvReport(Page<List<Map<String, Object>>> data, List<String> fields, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=products.csv");

        try (PrintWriter writer = response.getWriter()) {
            writer.println(String.join(",", fields));

            for (List<Map<String, Object>> productRows : data.getContent()) {
                for (Map<String, Object> product : productRows) {
                    String line = fields.stream()
                            .map(field -> Optional.ofNullable(product.get(field)).map(Object::toString).orElse(""))
                            .collect(Collectors.joining(","));
                    writer.println(line);
                }
            }
        }
    }

    private void generateXlsxReport(Page<List<Map<String, Object>>> data, List<String> fields, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < fields.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(fields.get(i));
            }

            int rowIndex = 1;

            for (List<Map<String, Object>> productRows : data.getContent()) {
                for (Map<String, Object> product : productRows) {
                    Row dataRow = sheet.createRow(rowIndex++);

                    for (int j = 0; j < fields.size(); j++) {
                        Cell cell = dataRow.createCell(j);
                        Object value = product.get(fields.get(j));
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                }
            }

            workbook.write(response.getOutputStream());
        }
    }

}
