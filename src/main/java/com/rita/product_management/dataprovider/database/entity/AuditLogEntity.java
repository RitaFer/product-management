package com.rita.product_management.dataprovider.database.entity;

import com.rita.product_management.core.domain.enums.ActionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit_logs")
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType action;

    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column()
    private String field;

    @Column(name = "old_value", length = 2000)
    private String oldValue;

    @Column(name = "new_value", length = 2000)
    private String newValue;

    @ManyToOne
    @JoinColumn(name = "modified_by", nullable = false)
    private UserEntity modifiedBy;

    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;

}
