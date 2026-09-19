package com.watashi.demobank.infrastructure.repository.account;

import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.domain.repositories.AccountRepository;
import com.watashi.demobank.infrastructure.repository.common.AbstractMemoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("memory")
public class MemoryAccountRepository extends AbstractMemoryRepository<Account> implements AccountRepository {

    public MemoryAccountRepository() {
        super(
                Account::getId,
                Account::setId,
                Account::getCreatedAt,
                Account::setCreatedAt,
                Account::setUpdatedAt
        );
    }
}
