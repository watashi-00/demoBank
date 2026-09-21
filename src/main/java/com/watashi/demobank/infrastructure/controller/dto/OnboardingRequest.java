package com.watashi.demobank.infrastructure.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OnboardingRequest(
        @NotBlank(message = "CPF is required")
        @Size(min = 11, max = 14, message = "CPF must be between 11 and 14 characters")
        String cpf,

        @NotNull(message = "Agency ID is required")
        Long agencyId,

        @NotBlank(message = "Account number is required")
        String accountNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email
) {
}
