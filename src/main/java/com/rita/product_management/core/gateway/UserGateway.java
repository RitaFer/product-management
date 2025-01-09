package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserGateway {
    User findUserByUsername(final String username);
    User findActiveUserByUsername(final String username);
    Optional<UserEntity> findByUsername(final String username);
    User findUserById(final String id);
    Page<User> findAll(final Pageable pageable);
    User save(final User user);
    void delete(final User user);
}
