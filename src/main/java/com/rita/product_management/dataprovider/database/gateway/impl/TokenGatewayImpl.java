package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.BusinessException;
import com.rita.product_management.core.domain.Token;
import com.rita.product_management.core.gateway.TokenGateway;
import com.rita.product_management.dataprovider.database.entity.TokenEntity;
import com.rita.product_management.dataprovider.database.repository.TokenRepository;
import com.rita.product_management.dataprovider.mapper.TokenMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TokenGatewayImpl implements TokenGateway {

    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    public TokenGatewayImpl(TokenRepository tokenRepository, TokenMapper tokenMapper) {
        this.tokenRepository = tokenRepository;
        this.tokenMapper = tokenMapper;
    }

    @Override
    public Token generateToken(String userId) {
        if (canGenerateNewToken(userId)) {
            Token token = new Token(userId);
            TokenEntity tokenSaved = tokenRepository.save(tokenMapper.fromModelToEntity(token));
            return tokenMapper.fromEntityToModel(tokenSaved);
        } else {
            throw new BusinessException(
                    "You can only generate a new token 1 minute after the last token was generated.");
        }
    }

    @Override
    public Boolean validateToken(String code) {
        Optional<Token> validToken =
                tokenRepository.findByTokenAndTokenUsedFalseAndExpiredAtIsAfter(code, LocalDateTime.now()).map(tokenMapper::fromEntityToModel);

        if (validToken.isEmpty()) {
            return false;
        } else {
            Token token = validToken.get();
            token.setTokenUsed(true);
            tokenRepository.save(tokenMapper.fromModelToEntity(token));
        }

        return validToken.get().getToken().equalsIgnoreCase(code);
    }

    @Override
    public Token findToken(String token) {
        return tokenRepository.findByToken(token).map(tokenMapper::fromEntityToModel).orElseThrow(
                () -> new NotFoundException("Token not found"));
    }

    private boolean canGenerateNewToken(String userId) {
        List<Token> lastCodes =
                tokenRepository.findByUserIdAndTokenUsedFalseOrderByCreatedAtDesc(userId).stream().map(tokenMapper::fromEntityToModel).toList();
        if (!lastCodes.isEmpty()) {
            LocalDateTime lastTokenTime = lastCodes.get(0).getCreatedAt();
            boolean canGenerate = lastTokenTime.plusMinutes(1).isBefore(LocalDateTime.now());

            if (canGenerate) {
                lastCodes.forEach(
                        token -> {
                            token.setTokenUsed(true);
                            tokenRepository.save(tokenMapper.fromModelToEntity(token));
                        });
            }

            return canGenerate;
        }
        return true;
    }
}
