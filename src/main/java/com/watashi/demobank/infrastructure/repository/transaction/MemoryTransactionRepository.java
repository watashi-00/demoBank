package com.watashi.demobank.infrastructure.repository.transaction;

import com.watashi.demobank.domain.entities.Transaction;
import com.watashi.demobank.domain.repositories.TransactionRepository;
import com.watashi.demobank.infrastructure.repository.common.AbstractMemoryRepository;

public class MemoryTransactionRepository extends AbstractMemoryRepository<Transaction> implements TransactionRepository {

    public MemoryTransactionRepository() {
        super(
                Transaction::getId,
                Transaction::setId,
                Transaction::getCreatedAt,
                Transaction::setCreatedAt,
                (Transaction, updatedAt) -> {} // ignore updatedAt
        );
    }
}
