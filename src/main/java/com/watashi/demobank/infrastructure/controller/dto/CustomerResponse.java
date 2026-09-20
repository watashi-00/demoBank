package com.watashi.demobank.infrastructure.controller.dto;

import com.watashi.demobank.domain.entities.Customer;

import java.time.Instant;

public record CustomerResponse(
        Long id,
        String cpf,
        Instant createdAt,
        Instant updatedAt
) {
    public static CustomerResponse fromEntity(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getCpf(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}
