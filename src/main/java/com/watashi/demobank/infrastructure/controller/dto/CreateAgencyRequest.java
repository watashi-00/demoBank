package com.watashi.demobank.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAgencyRequest(
        @NotNull(message = "Bank ID is required")
        Long bankId,

        @NotBlank(message = "Agency code is required")
        String code
) {
}
