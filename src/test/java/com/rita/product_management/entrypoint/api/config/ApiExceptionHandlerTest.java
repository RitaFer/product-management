package com.rita.product_management.entrypoint.api.config;

import com.rita.product_management.core.common.exception.BusinessException;
import com.rita.product_management.core.common.exception.NoChangesException;
import com.rita.product_management.core.common.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiExceptionHandlerTest {

    private ApiExceptionHandler apiExceptionHandler;

    @BeforeEach
    void setUp() {
        apiExceptionHandler = new ApiExceptionHandler();
    }

    @Test
    void givenUserNotFoundExceptionThenThrowResponseWithNotFoundStatus() {
        String errorMessage = "User not found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleUserNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().status());
        assertEquals(errorMessage, response.getBody().message());
        assertEquals(LocalDateTime.now().getYear(), response.getBody().timestamp().getYear());
    }

    @Test
    void givenBusinessExceptionThenThrowResponseWithBadRequestStatus() {
        String errorMessage = "Business logic error";
        BusinessException exception = new BusinessException(errorMessage);
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleUserNotFoundException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(errorMessage, response.getBody().message());
        assertEquals(LocalDateTime.now().getYear(), response.getBody().timestamp().getYear());
    }

    @Test
    void givenNoChangesExceptionThenThrowResponseWithBadRequestStatus() {
        String errorMessage = "No changes detected";
        NoChangesException exception = new NoChangesException(errorMessage);
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleNoChangesInUpdateException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
        assertEquals(errorMessage, response.getBody().message());
        assertEquals(LocalDateTime.now().getYear(), response.getBody().timestamp().getYear());
    }

}
