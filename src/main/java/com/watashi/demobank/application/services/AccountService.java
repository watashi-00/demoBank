package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.domain.repositories.AccountRepository;
import com.watashi.demobank.domain.repositories.CustomerRepository;
import com.watashi.demobank.infrastructure.controller.dto.CreateAccountRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Account createAccount(CreateAccountRequest request) {
        customerRepository.findById(request.customerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + request.customerId()));

        Account account = new Account(
                request.customerId(),
                request.agencyId(),
                request.accountNumber(),
                request.email()
        );

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = findById(accountId);
        account.deposit(amount);
        return accountRepository.save(account);
    }

    @Transactional
    public Account withdraw(Long accountId, BigDecimal amount) {
        Account account = findById(accountId);
        account.withdraw(amount);
        return accountRepository.save(account);
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        accountRepository.deleteById(id);
    }
}
