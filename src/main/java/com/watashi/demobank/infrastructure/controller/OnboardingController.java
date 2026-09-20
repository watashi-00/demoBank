package com.watashi.demobank.infrastructure.controller;

import com.watashi.demobank.application.services.CustomerOnboardingService;
import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.infrastructure.controller.dto.AccountResponse;
import com.watashi.demobank.infrastructure.controller.dto.OnboardingRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/onboarding")
public class OnboardingController {

    private final CustomerOnboardingService onboardingService;

    public OnboardingController(CustomerOnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> onboardCustomer(@Valid @RequestBody OnboardingRequest request) {
        Account createdAccount = onboardingService.onboardCustomer(request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/accounts/{id}")
                .buildAndExpand(createdAccount.getId())
                .toUri();
        return ResponseEntity.created(location).body(AccountResponse.fromEntity(createdAccount));
    }

    @PostMapping("/accounts/{accountId}/approve")
    public ResponseEntity<AccountResponse> approveAccount(@PathVariable Long accountId) {
        Account approvedAccount = onboardingService.approveAccount(accountId);
        return ResponseEntity.ok(AccountResponse.fromEntity(approvedAccount));
    }
}
