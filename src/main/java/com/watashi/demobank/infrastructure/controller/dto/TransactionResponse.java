package com.watashi.demobank.infrastructure.controller.dto;

import com.watashi.demobank.domain.entities.Transaction;
import com.watashi.demobank.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(
        Long id,
        TransactionType type,
        BigDecimal amount,
        Long fromAccountId,
        Long toAccountId,
        String notes,
        Instant createdAt
) {
    public static TransactionResponse fromEntity(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getFromAccountId(),
                transaction.getToAccountId(),
                transaction.getNotes(),
                transaction.getCreatedAt()
        );
    }
}
