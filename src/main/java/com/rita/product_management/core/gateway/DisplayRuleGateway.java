package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.DisplayRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DisplayRuleGateway {
    DisplayRule save(final DisplayRule rule);
    Page<DisplayRule> findAll(final Pageable pageable);
    DisplayRule delete(final String id);
}
