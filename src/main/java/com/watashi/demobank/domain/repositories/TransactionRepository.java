package com.watashi.demobank.domain.repositories;

import com.watashi.demobank.domain.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    Optional<Transaction>   findById(Long id);
    List<Transaction>       findAll();
    Transaction             save(Transaction transaction);
    void                    deleteById(Long id);
}
