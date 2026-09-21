package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.domain.entities.Transaction;
import com.watashi.demobank.domain.enums.AccountStatus;
import com.watashi.demobank.domain.enums.TransactionType;
import com.watashi.demobank.domain.repositories.AccountRepository;
import com.watashi.demobank.domain.repositories.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Account createSampleAccount(Long id, BigDecimal initialBalance) {
        Account account = new Account(1L, 1L, "12345678", "test@example.com");
        account.setId(id);
        account.setBalance(initialBalance);
        account.setStatus(AccountStatus.ACTIVE);
        return account;
    }

    @Test
    @DisplayName("Should deposit successfully into active account")
    void deposit_ShouldIncreaseBalanceAndSaveTransaction() {
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("500.00");
        BigDecimal depositAmount = new BigDecimal("150.00");
        Account account = createSampleAccount(accountId, initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = transactionService.deposit(accountId, depositAmount, "Test Deposit");

        assertNotNull(result);
        assertEquals(TransactionType.DEPOSIT, result.getType());
        assertEquals(depositAmount, result.getAmount());
        assertEquals(new BigDecimal("650.00"), account.getBalance());
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should withdraw successfully from active account with sufficient balance")
    void withdraw_ShouldDecreaseBalanceAndSaveTransaction() {
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("500.00");
        BigDecimal withdrawAmount = new BigDecimal("200.00");
        Account account = createSampleAccount(accountId, initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = transactionService.withdraw(accountId, withdrawAmount, "Test Withdraw");

        assertNotNull(result);
        assertEquals(TransactionType.WITHDRAW, result.getType());
        assertEquals(withdrawAmount, result.getAmount());
        assertEquals(new BigDecimal("300.00"), account.getBalance());
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should transfer successfully between two active accounts")
    void transfer_ShouldUpdateBalancesAndSaveTransaction() {
        Long fromId = 1L;
        Long toId = 2L;
        BigDecimal transferAmount = new BigDecimal("100.00");

        Account fromAccount = createSampleAccount(fromId, new BigDecimal("500.00"));
        Account toAccount = createSampleAccount(toId, new BigDecimal("200.00"));

        when(accountRepository.findById(fromId)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(toId)).thenReturn(Optional.of(toAccount));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = transactionService.transfer(fromId, toId, transferAmount, "Rent Transfer");

        assertNotNull(result);
        assertEquals(TransactionType.TRANSFER, result.getType());
        assertEquals(new BigDecimal("400.00"), fromAccount.getBalance());
        assertEquals(new BigDecimal("300.00"), toAccount.getBalance());
        verify(accountRepository, times(1)).save(fromAccount);
        verify(accountRepository, times(1)).save(toAccount);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should throw exception when transferring to the same account")
    void transfer_SameAccount_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.transfer(1L, 1L, new BigDecimal("50.00"), "Self")
        );

        assertEquals("Source and target accounts must be different for transfer", exception.getMessage());
        verify(accountRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should throw exception when depositing into inactive account")
    void deposit_InactiveAccount_ShouldThrowException() {
        Long accountId = 1L;
        Account inactiveAccount = createSampleAccount(accountId, new BigDecimal("100.00"));
        inactiveAccount.setStatus(AccountStatus.CLOSED);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(inactiveAccount));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> transactionService.deposit(accountId, new BigDecimal("50.00"), "Deposit")
        );

        assertEquals("Account with id 1 is not active", exception.getMessage());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find transaction by ID")
    void findById_WhenExists_ShouldReturnTransaction() {
        Long txId = 10L;
        Transaction expected = new Transaction(TransactionType.DEPOSIT, new BigDecimal("100.00"), null, 1L, "Notes");
        expected.setId(txId);

        when(transactionRepository.findById(txId)).thenReturn(Optional.of(expected));

        Transaction actual = transactionService.findById(txId);

        assertNotNull(actual);
        assertEquals(txId, actual.getId());
        verify(transactionRepository, times(1)).findById(txId);
    }

    @Test
    @DisplayName("Should find transactions by account ID")
    void findByAccountId_WhenAccountExists_ShouldReturnTransactions() {
        Long accountId = 1L;
        Account account = createSampleAccount(accountId, new BigDecimal("1000.00"));
        Transaction t1 = new Transaction(TransactionType.DEPOSIT, new BigDecimal("500.00"), null, accountId, "Dep");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.findByAccountId(accountId)).thenReturn(List.of(t1));

        List<Transaction> transactions = transactionService.findByAccountId(accountId);

        assertEquals(1, transactions.size());
        verify(transactionRepository, times(1)).findByAccountId(accountId);
    }
}
