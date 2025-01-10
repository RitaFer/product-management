package com.rita.product_management.entrypoint.api.dto.filters;

import java.time.LocalDateTime;

public record AuditFilter(
        String id,
        String action,
        String entityName,
        String entityId,
        String field,
        String oldValue,
        String newValue,
        String modifiedBy,
        LocalDateTime modifiedDatInitial,
        LocalDateTime modifiedDateFinal
) {
}
