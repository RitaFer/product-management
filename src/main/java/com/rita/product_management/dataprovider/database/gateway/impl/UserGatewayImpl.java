package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.BusinessException;
import com.rita.product_management.core.common.exception.UserNotFoundException;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import com.rita.product_management.dataprovider.database.repository.UserRepository;
import com.rita.product_management.dataprovider.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
@AllArgsConstructor
public class UserGatewayImpl implements UserGateway, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User findUserByUsername(String username) {
        validateInput(username, "Username cannot be null or empty.");
        log.debug("Searching for user by username: [{}]", username);
        return findUser(() -> userRepository.findByUsername(username),
                "User with username = " + username + ", not found.");
    }

    @Override
    public User findActiveUserByUsername(String username) {
        validateInput(username, "Username cannot be null or empty.");
        log.debug("Searching for active user by username: [{}]", username);
        return findUser(() -> userRepository.findByUsernameAndIsActiveIsTrue(username),
                "Active user with username = " + username + ", not found.");
    }

    @Override
    public User findUserById(String id) {
        validateInput(id, "User ID cannot be null or empty.");
        log.debug("Searching for user by id: [{}]", id);
        return findUser(() -> userRepository.findById(id),
                "User with id = " + id + ", not found.");
    }

    @Override
    public User save(User user) {
        log.debug("Saving user: [{}]", user.getUsername());
        User savedUser = userMapper.fromEntityToModel(userRepository.save(userMapper.fromModelToEntity(user)));
        log.debug("User [{}] saved successfully.", savedUser.getUsername());
        return savedUser;
    }

    @Override
    public void delete(User user) {
        log.debug("Deleting user: [{}]", user.getUsername());
        userRepository.delete(userMapper.fromModelToEntity(user));
        log.debug("User [{}] deleted successfully.", user.getUsername());
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        log.debug("Fetching all users with pagination: [{}]", pageable);
        return userRepository.findAll(pageable).map(userMapper::fromEntityToModel);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.debug("Loading user details for username: [{}]", username);
        User user = this.findActiveUserByUsername(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    private void validateInput(String input, String errorMessage) {
        if (StringUtils.isBlank(input)) {
            log.error(errorMessage);
            throw new BusinessException(errorMessage);
        }
    }

    private User findUser(Supplier<Optional<UserEntity>> userSupplier, String errorMessage) {
        return userSupplier.get()
                .map(userMapper::fromEntityToModel)
                .orElseThrow(() -> {
                    log.error(errorMessage);
                    return new UserNotFoundException(errorMessage);
                });
    }

}
