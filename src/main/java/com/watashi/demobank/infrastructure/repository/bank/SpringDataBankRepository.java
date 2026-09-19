package com.watashi.demobank.infrastructure.repository.bank;

import com.watashi.demobank.domain.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataBankRepository extends JpaRepository<Bank, Long> {
}
