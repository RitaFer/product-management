package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.UserNotFoundException;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.dataprovider.database.repository.UserRepository;
import com.rita.product_management.dataprovider.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.info("Searching for user by username: [{}]", username);
        return userRepository.findByUsername(username)
                .map(userMapper::fromEntityToModel)
                .orElseThrow(() -> {
                    log.error("User with username [{}] not found.", username);
                    return new UserNotFoundException("User with username = " + username + ", not found.");
                });
    }

    @Override
    public User findActiveUserByUsername(String username) {
        log.info("Searching for active user by username: [{}]", username);
        return userRepository.findByUsernameAndActiveIsTrue(username)
                .map(userMapper::fromEntityToModel)
                .orElseThrow(() -> {
                    log.error("Active user with username [{}] not found.", username);
                    return new UserNotFoundException("User with username = " + username + ", not found.");
                });
    }

    @Override
    public User findUserById(String id) {
        log.info("Searching for user by id: [{}]", id);
        return userRepository.findById(id)
                .map(userMapper::fromEntityToModel)
                .orElseThrow(() -> {
                    log.error("User with id [{}] not found.", id);
                    return new UserNotFoundException("User with id = " + id + ", not found.");
                });
    }

    @Override
    public User save(User user) {
        log.info("Saving user: [{}]", user);
        User savedUser = userMapper.fromEntityToModel(userRepository.save(userMapper.fromModelToEntity(user)));
        log.info("User saved successfully: [{}]", savedUser);
        return savedUser;
    }

    @Override
    public void delete(User user) {
        log.info("Deleting user: [{}]", user);
        userRepository.delete(userMapper.fromModelToEntity(user));
        log.info("User deleted successfully: [{}]", user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        log.info("Fetching all users with pagination: [{}]", pageable);
        Page<User> users = userRepository.findAll(pageable).map(userMapper::fromEntityToModel);
        log.info("Fetched [{}] users", users.getTotalElements());
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Loading user details for username: [{}]", username);
        User user = this.findActiveUserByUsername(username);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

        log.info("User details loaded successfully for username: [{}]", username);
        return userDetails;
    }

}
