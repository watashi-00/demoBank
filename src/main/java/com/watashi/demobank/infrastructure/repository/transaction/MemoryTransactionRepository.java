package com.watashi.demobank.infrastructure.repository.transaction;

import com.watashi.demobank.domain.entities.Transaction;
import com.watashi.demobank.domain.repositories.TransactionRepository;
import com.watashi.demobank.infrastructure.repository.common.AbstractMemoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@Profile("memory")
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

    @Override
    public List<Transaction> findByAccountId(Long accountId) {
        return findAll().stream()
                .filter(t -> Objects.equals(t.getFromAccountId(), accountId) || Objects.equals(t.getToAccountId(), accountId))
                .toList();
    }
}
