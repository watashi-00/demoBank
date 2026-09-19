package com.watashi.demobank.domain.repositories;

import com.watashi.demobank.domain.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account>   findById(Long id);
    List<Account>       findAll();
    Account             save(Account account);
    void                deleteById(Long id);
}
