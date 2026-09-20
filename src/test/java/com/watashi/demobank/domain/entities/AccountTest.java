package com.watashi.demobank.domain.entities;

import com.watashi.demobank.domain.enums.AccountStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    @DisplayName("Should initialize account with ZERO balance and ACTIVE status")
    void shouldInitializeAccountWithZeroBalanceAndActiveStatus() {
        Account account = new Account(1L, 1L, "12345-6", "user@test.com");

        assertNotNull(account);
        assertEquals(BigDecimal.ZERO, account.getBalance());
        assertEquals(AccountStatus.ACTIVE, account.getStatus());
        assertTrue(account.isActive());
    }

    @Test
    @DisplayName("Should deposit successfully into active account")
    void shouldDepositSuccessfully() {
        Account account = new Account(1L, 1L, "12345-6", "user@test.com");
        BigDecimal depositAmount = new BigDecimal("100.00");

        account.deposit(depositAmount);

        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when deposit amount is zero or negative")
    void shouldThrowExceptionWhenDepositIsNegativeOrZero() {
        Account account = new Account(1L, 1L, "12345-6", "user@test.com");

        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(new BigDecimal("-50.00")));
    }

    @Test
    @DisplayName("Should withdraw successfully when balance is sufficient")
    void shouldWithdrawSuccessfully() {
        Account account = new Account(1L, 1L, "12345-6", "user@test.com");
        account.deposit(new BigDecimal("200.00"));

        account.withdraw(new BigDecimal("50.00"));

        assertEquals(new BigDecimal("150.00"), account.getBalance());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when withdraw amount exceeds balance")
    void shouldThrowExceptionWhenInsufficientBalance() {
        Account account = new Account(1L, 1L, "12345-6", "user@test.com");
        account.deposit(new BigDecimal("50.00"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(new BigDecimal("100.00"))
        );

        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalStateException when operating on inactive account")
    void shouldThrowExceptionWhenOperatingOnInactiveAccount() {
        Account account = new Account(1L, 1L, "12345-6", "user@test.com");
        account.setStatus(AccountStatus.CLOSED);

        assertThrows(IllegalStateException.class, () -> account.deposit(new BigDecimal("50.00")));
        assertThrows(IllegalStateException.class, () -> account.withdraw(new BigDecimal("50.00")));
    }
}
