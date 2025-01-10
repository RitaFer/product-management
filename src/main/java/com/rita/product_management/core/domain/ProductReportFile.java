package com.rita.product_management.core.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReportFile {

    private String id;
    private Boolean isActive;
    private String name;
    private String active;
    private String sku;
    private String category;
    private BigDecimal costValue;
    private Double icms;
    private BigDecimal saleValue;
    private Long quantityInStock;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;

    public String getFieldValue(String selectedField) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> objectMap = mapper.convertValue(this, Map.class);

        return objectMap.get(selectedField).toString();
    }
}
