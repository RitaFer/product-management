package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.UserNotFoundException;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.dataprovider.database.repository.UserRepository;
import com.rita.product_management.dataprovider.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserGatewayImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserGatewayImpl userGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenUserExistsThenReturnUser_WhenFindUserByUsername() {
        String username = "testuser";
        User mockUser = new User();
        mockUser.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new com.rita.product_management.dataprovider.database.entity.UserEntity()));
        when(userMapper.fromEntityToModel(any())).thenReturn(mockUser);
        User user = userGateway.findUserByUsername(username);
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void givenUserDoesNotExistThenThrowException_WhenFindUserByUsername() {
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userGateway.findUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void givenActiveUserExistsThenReturnUser_WhenFindActiveUserByUsername() {
        String username = "activeuser";
        User mockUser = new User();
        mockUser.setUsername(username);
        when(userRepository.findByUsernameAndActiveIsTrue(username)).thenReturn(Optional.of(new com.rita.product_management.dataprovider.database.entity.UserEntity()));
        when(userMapper.fromEntityToModel(any())).thenReturn(mockUser);
        User user = userGateway.findActiveUserByUsername(username);
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        verify(userRepository, times(1)).findByUsernameAndActiveIsTrue(username);
    }

    @Test
    void givenActiveUserDoesNotExistThenThrowException_WhenFindActiveUserByUsername() {
        String username = "inactiveuser";
        when(userRepository.findByUsernameAndActiveIsTrue(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userGateway.findActiveUserByUsername(username));
        verify(userRepository, times(1)).findByUsernameAndActiveIsTrue(username);
    }

    @Test
    void givenUserExistsThenReturnUser_WhenFindUserById() {
        String id = "userId";
        User mockUser = new User();
        mockUser.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(new com.rita.product_management.dataprovider.database.entity.UserEntity()));
        when(userMapper.fromEntityToModel(any())).thenReturn(mockUser);
        User user = userGateway.findUserById(id);
        assertNotNull(user);
        assertEquals(id, user.getId());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void givenUserDoesNotExistThenThrowException_WhenFindUserById() {
        String id = "nonexistentId";
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userGateway.findUserById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void givenUserThenSaveAndReturnUser_WhenSave() {
        User user = new User();
        user.setUsername("newuser");
        when(userMapper.fromModelToEntity(any())).thenReturn(new com.rita.product_management.dataprovider.database.entity.UserEntity());
        when(userRepository.save(any())).thenReturn(new com.rita.product_management.dataprovider.database.entity.UserEntity());
        when(userMapper.fromEntityToModel(any())).thenReturn(user);
        User savedUser = userGateway.save(user);
        assertNotNull(savedUser);
        assertEquals(user.getUsername(), savedUser.getUsername());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void givenUserThenDelete_WhenDelete() {
        User user = new User();
        when(userMapper.fromModelToEntity(any())).thenReturn(new com.rita.product_management.dataprovider.database.entity.UserEntity());
        userGateway.delete(user);
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void givenPageableThenReturnPageOfUsers_WhenFindAll() {
        Pageable pageable = Pageable.unpaged();
        Page<com.rita.product_management.dataprovider.database.entity.UserEntity> mockPage = new PageImpl<>(Collections.emptyList());
        when(userRepository.findAll(pageable)).thenReturn(mockPage);
        when(userMapper.fromEntityToModel(any())).thenReturn(new User());
        Page<User> users = userGateway.findAll(pageable);
        assertNotNull(users);
        assertEquals(0, users.getTotalElements());
        verify(userRepository, times(1)).findAll(pageable);
    }

}
