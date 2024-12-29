package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.UserNotFoundException;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.dataprovider.database.repository.UserRepository;
import com.rita.product_management.dataprovider.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserGatewayImpl implements UserGateway, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserGatewayImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::fromEntityToModel).orElseThrow(() ->
                        new UserNotFoundException("User with username = "+ username +", not found."));
    }

    @Override
    public User findActiveUserByUsername(String username) {
        return userRepository.findByUsernameAndActiveIsTrue(username)
                .map(userMapper::fromEntityToModel).orElseThrow(() ->
                        new UserNotFoundException("User with username = "+ username +", not found."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = this.findActiveUserByUsername(username);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
