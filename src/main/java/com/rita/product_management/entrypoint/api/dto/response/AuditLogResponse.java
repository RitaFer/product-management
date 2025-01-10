package com.rita.product_management.entrypoint.api.dto.response;

import com.rita.product_management.core.domain.enums.ActionType;

import java.time.LocalDateTime;

public record AuditLogResponse(
        String id,
        ActionType action,
        String entityName,
        String entityId,
        String field,
        String oldValue,
        String newValue,
        UserProductResponse modifiedBy,
        LocalDateTime modifiedDate
) {
}
