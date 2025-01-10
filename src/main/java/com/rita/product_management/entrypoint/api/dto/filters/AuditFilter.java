package com.rita.product_management.entrypoint.api.dto.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditFilter {

    private String id;
    private String action;
    private String entityName;
    private String entityId;
    private String field;
    private String oldValue;
    private String newValue;
    private String modifiedBy;
    private LocalDateTime modifiedDateInitial;
    private LocalDateTime modifiedDateFinal;

}
