package com.watashi.demobank.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    @DisplayName("Should create Customer when CPF is valid")
    void shouldCreateCustomerWhenCpfIsValid() {
        String validCpf = "12345678901";

        Customer customer = new Customer(validCpf);

        assertNotNull(customer);
        assertEquals(validCpf, customer.getCpf());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when CPF is null")
    void shouldThrowExceptionWhenCpfIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Customer(null)
        );

        assertEquals("CPF cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when CPF is blank")
    void shouldThrowExceptionWhenCpfIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Customer("   ")
        );

        assertEquals("CPF cannot be null or empty", exception.getMessage());
    }
}
