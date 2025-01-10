package com.rita.product_management.core.domain;

import com.rita.product_management.core.domain.enums.ActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    private String id;
    private ActionType action;
    private String entityName;
    private String entityId;
    private String field;
    private String oldValue;
    private String newValue;
    private User modifiedBy;
    private LocalDateTime modifiedDate;

}
