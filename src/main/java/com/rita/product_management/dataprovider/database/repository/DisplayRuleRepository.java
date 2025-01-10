package com.rita.product_management.dataprovider.database.repository;

import com.rita.product_management.dataprovider.database.entity.DisplayRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisplayRuleRepository extends JpaRepository<DisplayRuleEntity, String> {

    boolean existsByIsActiveIsTrue();
    Optional<DisplayRuleEntity> findByRole(final String role);

}
