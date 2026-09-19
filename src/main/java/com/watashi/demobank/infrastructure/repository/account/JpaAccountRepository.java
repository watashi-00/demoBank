package com.watashi.demobank.infrastructure.repository.account;

import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.domain.repositories.AccountRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaAccountRepository implements AccountRepository {

    private final SpringDataAccountRepository springDataAccountRepository;

    public JpaAccountRepository(SpringDataAccountRepository springDataAccountRepository) {
        this.springDataAccountRepository = springDataAccountRepository;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return springDataAccountRepository.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return springDataAccountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return springDataAccountRepository.save(account);
    }

    @Override
    public void deleteById(Long id) {
        springDataAccountRepository.deleteById(id);
    }
}
