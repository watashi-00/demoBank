package com.watashi.demobank.infrastructure.repository.transaction;

import com.watashi.demobank.domain.entities.Transaction;
import com.watashi.demobank.domain.repositories.TransactionRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaTransactionRepository implements TransactionRepository {

    private final SpringDataTransactionRepository springDataTransactionRepository;

    public JpaTransactionRepository(SpringDataTransactionRepository springDataTransactionRepository) {
        this.springDataTransactionRepository = springDataTransactionRepository;
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return springDataTransactionRepository.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return springDataTransactionRepository.findAll();
    }

    @Override
    public Transaction save(Transaction transaction) {
        return springDataTransactionRepository.save(transaction);
    }

    @Override
    public void deleteById(Long id) {
        springDataTransactionRepository.deleteById(id);
    }
}
