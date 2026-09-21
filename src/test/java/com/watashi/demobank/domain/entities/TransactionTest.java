package com.watashi.demobank.domain.entities;

import com.watashi.demobank.domain.enums.TransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    @DisplayName("Should create valid Deposit transaction")
    void shouldCreateValidDepositTransaction() {
        Transaction t = new Transaction(TransactionType.DEPOSIT, new BigDecimal("100.00"), null, 1L, "Initial Deposit");

        assertNotNull(t);
        assertEquals(TransactionType.DEPOSIT, t.getType());
        assertEquals(new BigDecimal("100.00"), t.getAmount());
        assertNull(t.getFromAccountId());
        assertEquals(1L, t.getToAccountId());
        assertEquals("Initial Deposit", t.getNotes());
        assertNotNull(t.getCreatedAt());
    }

    @Test
    @DisplayName("Should create valid Withdraw transaction")
    void shouldCreateValidWithdrawTransaction() {
        Transaction t = new Transaction(TransactionType.WITHDRAW, new BigDecimal("50.00"), 1L, null, "ATM Withdrawal");

        assertNotNull(t);
        assertEquals(TransactionType.WITHDRAW, t.getType());
        assertEquals(new BigDecimal("50.00"), t.getAmount());
        assertEquals(1L, t.getFromAccountId());
        assertNull(t.getToAccountId());
    }

    @Test
    @DisplayName("Should create valid Transfer transaction")
    void shouldCreateValidTransferTransaction() {
        Transaction t = new Transaction(TransactionType.TRANSFER, new BigDecimal("200.00"), 1L, 2L, "Rent Payment");

        assertNotNull(t);
        assertEquals(TransactionType.TRANSFER, t.getType());
        assertEquals(1L, t.getFromAccountId());
        assertEquals(2L, t.getToAccountId());
    }

    @Test
    @DisplayName("Should throw exception when amount is zero or negative")
    void shouldThrowExceptionWhenAmountZeroOrNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transaction(TransactionType.DEPOSIT, BigDecimal.ZERO, null, 1L, "Invalid")
        );

        assertEquals("Transaction amount must be greater than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when transfer has same source and target account")
    void shouldThrowExceptionWhenTransferHasSameAccount() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Transaction(TransactionType.TRANSFER, new BigDecimal("10.00"), 1L, 1L, "Self Transfer")
        );

        assertEquals("Source and target accounts must be different for transfer", exception.getMessage());
    }
}
