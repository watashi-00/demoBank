package com.watashi.demobank.infrastructure.controller.dto;

import com.watashi.demobank.domain.entities.Bank;

import java.time.Instant;

public record BankResponse(
        Long id,
        String name,
        String number,
        Instant createdAt,
        Instant updatedAt
) {
    public static BankResponse fromEntity(Bank bank) {
        return new BankResponse(
                bank.getId(),
                bank.getName(),
                bank.getNumber(),
                bank.getCreatedAt(),
                bank.getUpdatedAt()
        );
    }
}
