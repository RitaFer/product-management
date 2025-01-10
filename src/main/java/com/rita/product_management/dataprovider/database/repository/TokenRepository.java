package com.rita.product_management.dataprovider.database.repository;

import com.rita.product_management.dataprovider.database.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, String> {

    List<TokenEntity> findByUserIdAndTokenUsedFalseOrderByCreatedAtDesc(String userId);
    Optional<TokenEntity> findByTokenAndTokenUsedFalseAndExpiredAtIsAfterAndTokenUsedIsFalse(String code, LocalDateTime expiration);

}
