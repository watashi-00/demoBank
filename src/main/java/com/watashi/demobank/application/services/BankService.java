package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Bank;
import com.watashi.demobank.domain.repositories.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankService {

    private final BankRepository repository;

    public BankService(BankRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Bank findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bank not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Bank> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Bank createBank(String name, String number) {
        Bank bank = new Bank(name, number);
        return repository.save(bank);
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}
