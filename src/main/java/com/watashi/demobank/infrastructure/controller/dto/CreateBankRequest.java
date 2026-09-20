package com.watashi.demobank.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateBankRequest(
        @NotBlank(message = "Bank name is required")
        String name,

        @NotBlank(message = "Bank number is required")
        @Pattern(regexp = "\\d{3}", message = "Bank number must be exactly 3 digits")
        String number
) {
}
