package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.domain.DisplayRule;
import com.rita.product_management.core.gateway.DisplayRuleGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DisplayRuleGatewayImpl implements DisplayRuleGateway {
    @Override
    public DisplayRule save(DisplayRule rule) {
        return null;
    }

    @Override
    public Page<DisplayRule> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public DisplayRule delete(String id) {
        return null;
    }
}
