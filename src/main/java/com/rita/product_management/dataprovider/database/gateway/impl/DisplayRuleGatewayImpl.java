package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.DisplayRuleNotFoundException;
import com.rita.product_management.core.domain.DisplayRule;
import com.rita.product_management.core.gateway.DisplayRuleGateway;
import com.rita.product_management.dataprovider.database.repository.DisplayRuleRepository;
import com.rita.product_management.dataprovider.mapper.DisplayRuleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DisplayRuleGatewayImpl implements DisplayRuleGateway {

    private final DisplayRuleRepository displayRuleRepository;
    private final DisplayRuleMapper displayRuleMapper;

    @Override
    public DisplayRule save(DisplayRule displayRule) {
        log.debug("Saving displayRule: [{}]", displayRule);
        DisplayRule savedDisplayRule = displayRuleMapper.fromEntityToModel(displayRuleRepository.save(displayRuleMapper.fromModelToEntity(displayRule)));
        log.debug("DisplayRule saved successfully: [{}]", savedDisplayRule);
        return savedDisplayRule;
    }

    @Override
    public DisplayRule findDisplayRuleById(String id) {
        log.debug("Searching for displayRule by id: [{}]", id);
        return displayRuleRepository.findById(id)
                .map(displayRuleMapper::fromEntityToModel)
                .orElseThrow(() -> {
                    log.error("DisplayRule with id [{}] not found.", id);
                    return new DisplayRuleNotFoundException("DisplayRule with id = " + id + ", not found.");
                });
    }

    @Override
    public Page<DisplayRule> findAll(Pageable pageable) {
        log.debug("Fetching all displayRules with pagination: [{}]", pageable);
        Page<DisplayRule> displayRules = displayRuleRepository.findAll(pageable).map(displayRuleMapper::fromEntityToModel);
        log.debug("Fetched [{}] displayRules", displayRules.getTotalElements());
        return displayRules;
    }

    @Override
    public void delete(String id) {
        log.debug("Deleting displayRule: [{}]", id);
        DisplayRule displayRule = findDisplayRuleById(id);
        displayRuleRepository.delete(displayRuleMapper.fromModelToEntity(displayRule));
        log.debug("DisplayRule deleted successfully: [{}]", displayRule);
    }
}
