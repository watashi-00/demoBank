package com.watashi.demobank.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgencyTest {

    @Test
    @DisplayName("Should create Agency when bankId and code are valid")
    void shouldCreateAgencyWhenValid() {
        Agency agency = new Agency(1L, "0001");

        assertNotNull(agency);
        assertEquals(1L, agency.getBankId());
        assertEquals("0001", agency.getCode());
        assertNotNull(agency.getCreatedAt());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when bankId is null")
    void shouldThrowExceptionWhenBankIdIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Agency(null, "0001")
        );

        assertEquals("Bank ID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when code is blank")
    void shouldThrowExceptionWhenCodeIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Agency(1L, "   ")
        );

        assertEquals("Agency code cannot be null or empty", exception.getMessage());
    }
}
