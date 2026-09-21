package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.domain.entities.Customer;
import com.watashi.demobank.domain.enums.AccountStatus;
import com.watashi.demobank.domain.repositories.AccountRepository;
import com.watashi.demobank.domain.repositories.CustomerRepository;
import com.watashi.demobank.infrastructure.controller.dto.OnboardingRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerOnboardingService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public CustomerOnboardingService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account onboardCustomer(OnboardingRequest request) {
        Customer customer = new Customer(request.cpf());
        Customer savedCustomer = customerRepository.save(customer);

        Account account = new Account(
                savedCustomer.getId(),
                request.agencyId(),
                request.accountNumber(),
                request.email()
        );
        account.setStatus(AccountStatus.PENDING);

        return accountRepository.save(account);
    }

    @Transactional
    public Account approveAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + accountId));

        if (!AccountStatus.PENDING.equals(account.getStatus())) {
            throw new IllegalStateException("Only pending accounts can be approved");
        }

        account.setStatus(AccountStatus.ACTIVE);
        return accountRepository.save(account);
    }
}
