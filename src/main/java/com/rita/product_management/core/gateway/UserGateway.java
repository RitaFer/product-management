package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.user.User;

public interface UserGateway {
    User findUserByUsername(final String username);
    User findActiveUserByUsername(final String username);
}
