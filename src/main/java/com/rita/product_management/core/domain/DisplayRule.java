package com.rita.product_management.core.domain;

import com.rita.product_management.core.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayRule {

    private String id;
    private Boolean isActive;
    private List<String> hiddenFields;
    private UserType role;

}
