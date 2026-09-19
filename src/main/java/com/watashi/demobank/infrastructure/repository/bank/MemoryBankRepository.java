package com.watashi.demobank.infrastructure.repository.bank;

import com.watashi.demobank.domain.entities.Bank;
import com.watashi.demobank.domain.repositories.BankRepository;
import com.watashi.demobank.infrastructure.repository.common.AbstractMemoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("memory")
public class MemoryBankRepository extends AbstractMemoryRepository<Bank> implements BankRepository {

    public MemoryBankRepository() {
        super(
                Bank::getId,
                Bank::setId,
                Bank::getCreatedAt,
                Bank::setCreatedAt,
                Bank::setUpdatedAt
        );
    }
}
