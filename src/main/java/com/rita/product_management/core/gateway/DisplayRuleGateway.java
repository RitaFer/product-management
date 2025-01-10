package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.DisplayRule;
import com.rita.product_management.core.domain.enums.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DisplayRuleGateway {

    DisplayRule save(final DisplayRule rule);
    DisplayRule findById(final String id);
    List<String> getHiddenFieldsForRole(final UserType role);
    Boolean existsAnotherActiveDisplayRule();
    Page<DisplayRule> findAll(final Pageable pageable);
    void delete(final String id);

}
