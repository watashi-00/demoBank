package com.watashi.demobank.infrastructure.controller.dto;

import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.domain.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountResponse(
        Long id,
        Long customerId,
        Long agencyId,
        String accountNumber,
        String email,
        BigDecimal balance,
        AccountStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static AccountResponse fromEntity(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getCustomerId(),
                account.getAgencyId(),
                account.getAccountNumber(),
                account.getEmail(),
                account.getBalance(),
                account.getStatus(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}
