package com.watashi.demobank.infrastructure.repository.transaction;

import com.watashi.demobank.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataTransactionRepository extends JpaRepository<Transaction, Long> {
}
