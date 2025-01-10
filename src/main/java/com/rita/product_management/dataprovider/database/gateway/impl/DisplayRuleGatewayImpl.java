package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.DisplayRuleNotFoundException;
import com.rita.product_management.core.domain.DisplayRule;
import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.gateway.DisplayRuleGateway;
import com.rita.product_management.dataprovider.database.entity.DisplayRuleEntity;
import com.rita.product_management.dataprovider.database.repository.DisplayRuleRepository;
import com.rita.product_management.dataprovider.mapper.DisplayRuleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DisplayRuleGatewayImpl implements DisplayRuleGateway {

    private final DisplayRuleMapper displayRuleMapper;
    private final DisplayRuleRepository displayRuleRepository;

    @Override
    public DisplayRule save(DisplayRule displayRule) {
        log.debug("Saving DisplayRule with details: [{}]", displayRule);
        DisplayRule savedDisplayRule = displayRuleMapper.fromEntityToModel(
                displayRuleRepository.save(displayRuleMapper.fromModelToEntity(displayRule))
        );
        log.debug("DisplayRule saved successfully: [{}]", savedDisplayRule);
        return savedDisplayRule;
    }

    @Override
    public DisplayRule findById(String id) {
        log.debug("Searching for DisplayRule by ID: [{}]", id);
        return findDisplayRuleOrThrow(id);
    }

    @Override
    public List<String> getHiddenFieldsForRole(UserType role) {
        DisplayRuleEntity rule = displayRuleRepository
                .findByRoleAndIsActiveIsTrue(role)
                .orElseThrow(() ->
                new DisplayRuleNotFoundException("DisplayRule with role = " + role + " not found."));
        return rule.getHiddenFields();
    }

    @Override
    public Boolean existsAnotherActiveDisplayRule() {
        log.debug("Checking if another active DisplayRule exists...");
        boolean exists = displayRuleRepository.existsByIsActiveIsTrue();
        log.debug("Exists another active DisplayRule: [{}]", exists);
        return exists;
    }

    @Override
    public Page<DisplayRule> findAll(Pageable pageable) {
        log.debug("Fetching all DisplayRules with pagination: [{}]", pageable);
        Page<DisplayRule> displayRules = displayRuleRepository.findAll(pageable).map(displayRuleMapper::fromEntityToModel);
        log.debug("Fetched [{}] DisplayRules.", displayRules.getTotalElements());
        return displayRules;
    }

    @Override
    public void delete(String id) {
        log.debug("Deleting DisplayRule with ID: [{}]", id);
        DisplayRule displayRule = findDisplayRuleOrThrow(id);
        displayRuleRepository.delete(displayRuleMapper.fromModelToEntity(displayRule));
        log.debug("DisplayRule [{}] deleted successfully.", id);
    }

    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            log.error("The display rule id cannot be null or empty.");
            throw new IllegalArgumentException("The display rule id cannot be null or empty.");
        }
    }

    private DisplayRule findDisplayRuleOrThrow(String id) {
        validateId(id);
        return displayRuleRepository.findById(id)
                .map(displayRuleMapper::fromEntityToModel)
                .orElseThrow(() -> {
                    log.error("DisplayRule with ID [{}] not found.", id);
                    return new DisplayRuleNotFoundException("DisplayRule with ID = " + id + " not found.");
                });
    }
}

