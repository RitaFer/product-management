package com.rita.product_management.entrypoint.api.config;

import com.rita.product_management.core.common.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApiExceptionHandler {

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

}
