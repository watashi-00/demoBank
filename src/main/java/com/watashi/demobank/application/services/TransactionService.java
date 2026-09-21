package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.domain.entities.Transaction;
import com.watashi.demobank.domain.enums.TransactionType;
import com.watashi.demobank.domain.repositories.AccountRepository;
import com.watashi.demobank.domain.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Transaction deposit(Long accountId, BigDecimal amount, String notes) {
        Account account = findActiveAccount(accountId);
        account.deposit(amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount, null, accountId, notes);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Long accountId, BigDecimal amount, String notes) {
        Account account = findActiveAccount(accountId);
        account.withdraw(amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction(TransactionType.WITHDRAW, amount, accountId, null, notes);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String notes) {
        if (fromAccountId == null || toAccountId == null) {
            throw new IllegalArgumentException("Source and target account IDs are required for transfer");
        }
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Source and target accounts must be different for transfer");
        }

        Account fromAccount = findActiveAccount(fromAccountId);
        Account toAccount = findActiveAccount(toAccountId);

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction(TransactionType.TRANSFER, amount, fromAccountId, toAccountId, notes);
        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Transaction> findByAccountId(Long accountId) {
        findActiveAccount(accountId); // verify account exists
        return transactionRepository.findByAccountId(accountId);
    }

    private Account findActiveAccount(Long accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + accountId));
        if (!account.isActive()) {
            throw new IllegalStateException("Account with id " + accountId + " is not active");
        }
        return account;
    }
}
