package com.watashi.demobank.domain.repositories;

import com.watashi.demobank.domain.entities.Bank;

import java.util.List;
import java.util.Optional;

public interface BankRepository {
    Optional<Bank>      findById(Long id);
    List<Bank>          findAll();
    Bank                save(Bank bank);
    void                deleteById(Long id);
}
