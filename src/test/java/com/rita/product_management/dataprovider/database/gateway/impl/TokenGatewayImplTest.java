package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.BusinessException;
import com.rita.product_management.core.domain.Token;
import com.rita.product_management.dataprovider.database.entity.TokenEntity;
import com.rita.product_management.dataprovider.database.repository.TokenRepository;
import com.rita.product_management.dataprovider.mapper.TokenMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenGatewayImplTest {
//
//    @Mock
//    private TokenRepository tokenRepository;
//
//    @Mock
//    private TokenMapper tokenMapper;
//
//    @InjectMocks
//    private TokenGatewayImpl tokenGateway;
//
//    private final String userId = "test-user-id";
//    private final String tokenCode = "test-token";
//    private final Token token = new Token(userId);
//    private final TokenEntity tokenEntity = new TokenEntity();
//
//    @BeforeEach
//    void setup() {
//        token.setToken(tokenCode);
//        token.setCreatedAt(LocalDateTime.now());
//
//        tokenEntity.setToken(tokenCode);
//        tokenEntity.setUserId(userId);
//    }
//
//    @Test
//    void givenValidUserId_whenGenerateToken_thenReturnNewToken() {
//        when(tokenRepository.findByUserIdAndTokenUsedFalseOrderByCreatedAtDesc(userId)).thenReturn(Collections.emptyList());
//        when(tokenMapper.fromModelToEntity(any(Token.class))).thenReturn(tokenEntity);
//        when(tokenRepository.save(any(TokenEntity.class))).thenReturn(tokenEntity);
//        when(tokenMapper.fromEntityToModel(tokenEntity)).thenReturn(token);
//        Token result = tokenGateway.generateToken(userId);
//        assertNotNull(result);
//        assertEquals(tokenCode, result.getToken());
//        verify(tokenRepository).save(any(TokenEntity.class));
//    }
//
//    @Test
//    void givenRecentToken_whenGenerateToken_thenThrowBusinessException() {
//        when(tokenRepository.findByUserIdAndTokenUsedFalseOrderByCreatedAtDesc(userId)).thenReturn(List.of(tokenEntity));
//        when(tokenMapper.fromEntityToModel(any(TokenEntity.class))).thenReturn(token);
//        assertThrows(BusinessException.class, () -> tokenGateway.generateToken(userId));
//        verify(tokenRepository, never()).save(any());
//    }
//
//    @Test
//    void givenValidTokenCode_whenValidateToken_thenReturnTrue() {
//        token.setExpiredAt(LocalDateTime.now().plusMinutes(5)); // Not expired
//        when(tokenRepository.findByTokenAndTokenUsedFalseAndExpiredAtIsAfter(eq(tokenCode), any(LocalDateTime.class)))
//                .thenReturn(Optional.of(tokenEntity));
//        when(tokenMapper.fromEntityToModel(tokenEntity)).thenReturn(token);
//        when(tokenMapper.fromModelToEntity(token)).thenReturn(tokenEntity);
//        Boolean result = tokenGateway.validateToken(tokenCode);
//        assertTrue(result);
//        verify(tokenRepository).save(any(TokenEntity.class));
//    }
//
//    @Test
//    void givenInvalidTokenCode_whenValidateToken_thenReturnFalse() {
//        when(tokenRepository.findByTokenAndTokenUsedFalseAndExpiredAtIsAfter(eq(tokenCode), any(LocalDateTime.class)))
//                .thenReturn(Optional.empty());
//        Boolean result = tokenGateway.validateToken(tokenCode);
//        assertFalse(result);
//        verify(tokenRepository, never()).save(any());
//    }
//
//    @Test
//    void givenValidTokenCode_whenFindToken_thenReturnToken() {
//        when(tokenRepository.findByToken(tokenCode)).thenReturn(Optional.of(tokenEntity));
//        when(tokenMapper.fromEntityToModel(tokenEntity)).thenReturn(token);
//        Token result = tokenGateway.findToken(tokenCode);
//        assertNotNull(result);
//        assertEquals(tokenCode, result.getToken());
//    }
//
//    @Test
//    void givenInvalidTokenCode_whenFindToken_thenThrowNotFoundException() {
//        when(tokenRepository.findByToken(tokenCode)).thenReturn(Optional.empty());
//        assertThrows(NotFoundException.class, () -> tokenGateway.findToken(tokenCode));
//    }
}
