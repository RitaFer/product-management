package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserGateway {

    User findUserByUsername(final String username);
    User findActiveUserByUsername(final String username);
    User findUserById(final String id);
    Page<User> findAll(final Pageable pageable);
    User save(final User user);
    void delete(final User user);

}
