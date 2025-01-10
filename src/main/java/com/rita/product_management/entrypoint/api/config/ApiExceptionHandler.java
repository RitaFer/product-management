package com.rita.product_management.entrypoint.api.config;

import com.rita.product_management.core.common.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessage> businessException(BusinessException ex){
        var errorMessage = new ErrorMessage(BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(NoChangesException.class)
    public ResponseEntity<ErrorMessage> handleNoChangesInUpdateException(NoChangesException ex){
        var errorMessage = new ErrorMessage(BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        String message = "Validation error: "+ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        var errorMessage = new ErrorMessage(BAD_REQUEST.value(), message, LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }

    @ResponseStatus(value = FORBIDDEN)
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorMessage> handleAuthenticationFailedException(AuthenticationFailedException e) {
        var errorMessage = new ErrorMessage(FORBIDDEN.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, FORBIDDEN);
    }

    @ResponseStatus(value = FORBIDDEN)
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessage> handleInvalidTokenException(InvalidTokenException e) {
        var errorMessage = new ErrorMessage(FORBIDDEN.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, FORBIDDEN);
    }

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException ex){
        var errorMessage = new ErrorMessage(NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, NOT_FOUND);
    }

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCategoryNotFoundException(CategoryNotFoundException ex){
        var errorMessage = new ErrorMessage(NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, NOT_FOUND);
    }

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler(DisplayRuleNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleDisplayRuleNotFoundException(DisplayRuleNotFoundException ex){
        var errorMessage = new ErrorMessage(NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, NOT_FOUND);
    }

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler(AuditNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleAuditNotFoundException(AuditNotFoundException ex){
        var errorMessage = new ErrorMessage(NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, NOT_FOUND);
    }

    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JwtParsingException.class)
    public ResponseEntity<ErrorMessage> handleJwtParsingException(){
        var errorMessage = new ErrorMessage(INTERNAL_SERVER_ERROR.value(), null, LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(){
        var errorMessage = new ErrorMessage(INTERNAL_SERVER_ERROR.value(), null, LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, INTERNAL_SERVER_ERROR);
    }

}
