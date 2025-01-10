package com.rita.product_management.dataprovider.database.entity;

import com.rita.product_management.core.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "display_rules")
public class DisplayRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "hidden_fields", nullable = false)
    private List<String> hiddenFields;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType role = UserType.STOCKIST;

}
