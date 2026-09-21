package com.watashi.demobank.infrastructure.controller.dto;

import com.watashi.demobank.domain.entities.Agency;

import java.time.Instant;

public record AgencyResponse(
        Long id,
        Long bankId,
        String code,
        Instant createdAt,
        Instant updatedAt
) {
    public static AgencyResponse fromEntity(Agency agency) {
        return new AgencyResponse(
                agency.getId(),
                agency.getBankId(),
                agency.getCode(),
                agency.getCreatedAt(),
                agency.getUpdatedAt()
        );
    }
}
