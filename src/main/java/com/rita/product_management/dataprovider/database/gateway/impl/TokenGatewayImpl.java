package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.BusinessException;
import com.rita.product_management.core.domain.Token;
import com.rita.product_management.core.gateway.TokenGateway;
import com.rita.product_management.dataprovider.database.entity.TokenEntity;
import com.rita.product_management.dataprovider.database.repository.TokenRepository;
import com.rita.product_management.dataprovider.mapper.TokenMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TokenGatewayImpl implements TokenGateway {

    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    @Override
    public void save(Token token) {
        tokenRepository.save(tokenMapper.fromModelToEntity(token));
    }

    @Override
    public Token generateToken(String userId) {
        log.info("Attempting to generate a new token for userId: [{}]", userId);

        if (canGenerateNewToken(userId)) {
            Token token = new Token(userId);
            log.debug("Generated new token for userId: [{}], token: [{}]", userId, token.getToken());

            TokenEntity tokenSaved = tokenRepository.save(tokenMapper.fromModelToEntity(token));
            log.info("Token successfully saved for userId: [{}], token: [{}]", userId, tokenSaved.getToken());

            return tokenMapper.fromEntityToModel(tokenSaved);
        } else {
            log.warn("Token generation blocked for userId: [{}]. Reason: Token generation attempted within 1 minute of the last token.", userId);
            throw new BusinessException(
                    "You can only generate a new token 1 minute after the last token was generated.");
        }
    }

    @Override
    public Optional<Token> validateToken(String code) {
        log.info("Validating token: [{}]", code);

        return tokenRepository
                .findByTokenAndTokenUsedFalseAndExpiredAtIsAfterAndTokenUsedIsFalse(code, LocalDateTime.now())
                .map(tokenMapper::fromEntityToModel);
    }

    private boolean canGenerateNewToken(String userId) {
        log.debug("Checking if a new token can be generated for userId: [{}]", userId);

        List<Token> lastCodes = tokenRepository
                .findByUserIdAndTokenUsedFalseOrderByCreatedAtDesc(userId).stream()
                .map(tokenMapper::fromEntityToModel)
                .toList();

        if (!lastCodes.isEmpty()) {
            LocalDateTime lastTokenTime = lastCodes.getFirst().getCreatedAt();
            boolean canGenerate = lastTokenTime.plusMinutes(1).isBefore(LocalDateTime.now());

            if (canGenerate) {
                log.debug("Marking previous tokens as used for userId: [{}]", userId);
                lastCodes.forEach(token -> {
                    token.setTokenUsed(true);
                    tokenRepository.save(tokenMapper.fromModelToEntity(token));
                });
            } else {
                log.warn("Cannot generate a new token for userId: [{}]. Last token generated at: [{}]", userId, lastTokenTime);
            }

            return canGenerate;
        }

        log.debug("No previous tokens found for userId: [{}]. Allowing token generation.", userId);
        return true;
    }

}
