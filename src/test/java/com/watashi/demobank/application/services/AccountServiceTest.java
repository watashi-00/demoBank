package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.domain.entities.Customer;
import com.watashi.demobank.domain.repositories.AccountRepository;
import com.watashi.demobank.domain.repositories.CustomerRepository;
import com.watashi.demobank.infrastructure.controller.dto.CreateAccountRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("Should create account when customer exists")
    void createAccount_WhenCustomerExists_ShouldCreateAndReturnAccount() {
        Long customerId = 1L;
        Long agencyId = 10L;
        CreateAccountRequest request = new CreateAccountRequest(customerId, agencyId, "12345-6", "user@test.com");

        Customer customer = new Customer("12345678901");
        customer.setId(customerId);

        Account expectedAccount = new Account(customerId, agencyId, "12345-6", "user@test.com");
        expectedAccount.setId(100L);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(expectedAccount);

        Account createdAccount = accountService.createAccount(request);

        assertNotNull(createdAccount);
        assertEquals(100L, createdAccount.getId());
        assertEquals("12345-6", createdAccount.getAccountNumber());
        verify(customerRepository, times(1)).findById(customerId);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating account for non-existing customer")
    void createAccount_WhenCustomerNotFound_ShouldThrowException() {
        CreateAccountRequest request = new CreateAccountRequest(999L, 10L, "12345-6", "user@test.com");

        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> accountService.createAccount(request)
        );

        assertEquals("Customer not found with id: 999", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should deposit amount into account")
    void deposit_WhenAccountExists_ShouldDepositAmount() {
        Long accountId = 100L;
        Account account = new Account(1L, 10L, "12345-6", "user@test.com");
        account.setId(accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account updatedAccount = accountService.deposit(accountId, new BigDecimal("150.00"));

        assertEquals(new BigDecimal("150.00"), updatedAccount.getBalance());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(account);
    }
}
