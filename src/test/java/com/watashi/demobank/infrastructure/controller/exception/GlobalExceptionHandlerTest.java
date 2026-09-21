package com.watashi.demobank.infrastructure.controller.exception;

import com.watashi.demobank.infrastructure.controller.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Should handle IllegalStateException with HTTP 400 Bad Request")
    void shouldHandleIllegalStateException() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/onboarding/accounts/1/approve");

        IllegalStateException ex = new IllegalStateException("Only pending accounts can be approved");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalStateException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Business Rule Violation", response.getBody().error());
        assertEquals("Only pending accounts can be approved", response.getBody().message());
        assertEquals("/api/v1/onboarding/accounts/1/approve", response.getBody().path());
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException for not found with HTTP 404")
    void shouldHandleIllegalArgumentExceptionNotFound() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/accounts/99");

        IllegalArgumentException ex = new IllegalArgumentException("Account not found with id: 99");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalArgumentException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Not Found", response.getBody().error());
        assertEquals("Account not found with id: 99", response.getBody().message());
    }
}
