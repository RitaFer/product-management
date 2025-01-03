package com.rita.product_management.dataprovider.database.repository;

import com.rita.product_management.dataprovider.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(final String username);
    Optional<UserEntity> findByUsernameAndActiveIsTrue(final String username);
    Optional<UserEntity> findByIdAndActiveIsTrue(final String id);

}
