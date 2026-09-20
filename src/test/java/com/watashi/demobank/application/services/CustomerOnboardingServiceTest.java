package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.domain.entities.Customer;
import com.watashi.demobank.domain.enums.AccountStatus;
import com.watashi.demobank.domain.repositories.AccountRepository;
import com.watashi.demobank.domain.repositories.CustomerRepository;
import com.watashi.demobank.infrastructure.controller.dto.OnboardingRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerOnboardingServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CustomerOnboardingService onboardingService;

    @Test
    @DisplayName("Should create customer and pending account during onboarding")
    void onboardCustomer_WithValidRequest_ShouldCreateCustomerAndPendingAccount() {
        OnboardingRequest request = new OnboardingRequest("12345678901", 10L, "12345-6", "user@test.com");

        Customer savedCustomer = new Customer("12345678901");
        savedCustomer.setId(1L);

        Account savedAccount = new Account(1L, 10L, "12345-6", "user@test.com");
        savedAccount.setId(100L);
        savedAccount.setStatus(AccountStatus.PENDING);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        Account resultAccount = onboardingService.onboardCustomer(request);

        assertNotNull(resultAccount);
        assertEquals(100L, resultAccount.getId());
        assertEquals(AccountStatus.PENDING, resultAccount.getStatus());

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should approve pending account and change status to ACTIVE")
    void approveAccount_WhenPending_ShouldChangeStatusToActive() {
        Long accountId = 100L;
        Account pendingAccount = new Account(1L, 10L, "12345-6", "user@test.com");
        pendingAccount.setId(accountId);
        pendingAccount.setStatus(AccountStatus.PENDING);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(pendingAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account approvedAccount = onboardingService.approveAccount(accountId);

        assertEquals(AccountStatus.ACTIVE, approvedAccount.getStatus());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(pendingAccount);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when approving account that is not PENDING")
    void approveAccount_WhenNotPending_ShouldThrowException() {
        Long accountId = 100L;
        Account activeAccount = new Account(1L, 10L, "12345-6", "user@test.com");
        activeAccount.setId(accountId);
        activeAccount.setStatus(AccountStatus.ACTIVE);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(activeAccount));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> onboardingService.approveAccount(accountId)
        );

        assertEquals("Only pending accounts can be approved", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }
}
