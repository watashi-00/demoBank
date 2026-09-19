package com.watashi.demobank.infrastructure.repository.bank;

import com.watashi.demobank.domain.entities.Bank;
import com.watashi.demobank.domain.repositories.BankRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaBankRepository implements BankRepository {

    private final SpringDataBankRepository springDataBankRepository;

    public JpaBankRepository(SpringDataBankRepository springDataBankRepository) {
        this.springDataBankRepository = springDataBankRepository;
    }

    @Override
    public Optional<Bank> findById(Long id) {
        return springDataBankRepository.findById(id);
    }

    @Override
    public List<Bank> findAll() {
        return springDataBankRepository.findAll();
    }

    @Override
    public Bank save(Bank bank) {
        return springDataBankRepository.save(bank);
    }

    @Override
    public void deleteById(Long id) {
        springDataBankRepository.deleteById(id);
    }
}
