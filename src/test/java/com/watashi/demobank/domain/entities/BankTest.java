package com.watashi.demobank.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @Test
    @DisplayName("Should create Bank when name and 3-digit number are valid")
    void shouldCreateBankWhenValid() {
        Bank bank = new Bank("DemoBank", "001");

        assertNotNull(bank);
        assertEquals("DemoBank", bank.getName());
        assertEquals("001", bank.getNumber());
        assertNotNull(bank.getCreatedAt());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Bank("   ", "001")
        );

        assertEquals("Bank name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when number is blank")
    void shouldThrowExceptionWhenNumberIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Bank("DemoBank", "  ")
        );

        assertEquals("Bank number cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when number does not have 3 digits")
    void shouldThrowExceptionWhenNumberIsNot3Digits() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Bank("DemoBank", "12")
        );

        assertEquals("Bank number must be exactly 3 digits", exception.getMessage());
    }
}
