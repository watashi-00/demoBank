package com.watashi.demobank.infrastructure.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(
        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotNull(message = "Agency ID is required")
        Long agencyId,

        @NotBlank(message = "Account number is required")
        String accountNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email
) {
}
