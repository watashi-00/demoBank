package com.watashi.demobank.infrastructure.repository.account;

import com.watashi.demobank.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataAccountRepository extends JpaRepository<Account,Long> {
}
