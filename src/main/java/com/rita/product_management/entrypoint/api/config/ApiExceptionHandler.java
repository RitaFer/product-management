package com.rita.product_management.entrypoint.api.config;

import com.rita.product_management.core.common.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.Map;
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
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        var errorMessage = new ErrorMessage(BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }

    @ResponseStatus(value = CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = ex.getCause().getMessage();
        var errorMessage = new ErrorMessage(CONFLICT.value(), "The resource already exists.", LocalDateTime.now());
        if (message.contains("Duplicate entry")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of("error", "Data integrity violation", "message", ex.getMessage())
        );
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
    public ResponseEntity<ErrorMessage> handleException(Exception e){
        var errorMessage = new ErrorMessage(INTERNAL_SERVER_ERROR.value(), null, LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException e){
        var errorMessage = new ErrorMessage(INTERNAL_SERVER_ERROR.value(), null, LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, INTERNAL_SERVER_ERROR);
    }

}
