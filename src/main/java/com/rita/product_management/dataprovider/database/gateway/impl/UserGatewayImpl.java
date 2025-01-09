package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.UserNotFoundException;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import com.rita.product_management.dataprovider.database.repository.UserRepository;
import com.rita.product_management.dataprovider.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserGatewayImpl implements UserGateway, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User findUserByUsername(String username) {
        log.debug("Searching for user by username: [{}]", username);
        return userRepository.findByUsername(username)
                .map(userMapper::fromEntityToModel)
                .orElseThrow(() -> {
                    log.error("User with username [{}] not found.", username);
                    return new UserNotFoundException("User with username = " + username + ", not found.");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsernameAndIsActiveIsTrue(username);
    }

    @Override
    public User findActiveUserByUsername(String username) {
        log.debug("Searching for active user by username: [{}]", username);
        return userRepository.findByUsernameAndIsActiveIsTrue(username)
                .map(userMapper::fromEntityToModel)
                .orElseThrow(() -> {
                    log.error("Active user with username [{}] not found.", username);
                    return new UserNotFoundException("User with username = " + username + ", not found.");
                });
    }

    @Override
    public User findUserById(String id) {
        log.debug("Searching for user by id: [{}]", id);
        return userRepository.findById(id)
                .map(userMapper::fromEntityToModel)
                .orElseThrow(() -> {
                    log.error("User with id [{}] not found.", id);
                    return new UserNotFoundException("User with id = " + id + ", not found.");
                });
    }

    @Override
    public User save(User user) {
        log.debug("Saving user: [{}]", user);
        User savedUser = userMapper.fromEntityToModel(userRepository.save(userMapper.fromModelToEntity(user)));
        log.debug("User saved successfully: [{}]", savedUser);
        return savedUser;
    }

    @Override
    public void delete(User user) {
        log.debug("Deleting user: [{}]", user);
        userRepository.delete(userMapper.fromModelToEntity(user));
        log.debug("User deleted successfully: [{}]", user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        log.debug("Fetching all users with pagination: [{}]", pageable);
        Page<User> users = userRepository.findAll(pageable).map(userMapper::fromEntityToModel);
        log.debug("Fetched [{}] users", users.getTotalElements());
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.debug("Loading user details for username: [{}]", username);
        User user = this.findActiveUserByUsername(username);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

        log.debug("User details loaded successfully for username: [{}]", username);
        return userDetails;
    }

}
