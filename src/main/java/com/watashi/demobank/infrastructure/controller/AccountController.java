package com.watashi.demobank.infrastructure.controller;

import com.watashi.demobank.application.services.AccountService;
import com.watashi.demobank.domain.entities.Account;
import com.watashi.demobank.infrastructure.controller.dto.AccountResponse;
import com.watashi.demobank.infrastructure.controller.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        Account account = service.findById(id);
        return ResponseEntity.ok(AccountResponse.fromEntity(account));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account createdAccount = service.createAccount(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAccount.getId())
                .toUri();
        return ResponseEntity.created(location).body(AccountResponse.fromEntity(createdAccount));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        Account updatedAccount = service.deposit(id, amount);
        return ResponseEntity.ok(AccountResponse.fromEntity(updatedAccount));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {
        Account updatedAccount = service.withdraw(id, amount);
        return ResponseEntity.ok(AccountResponse.fromEntity(updatedAccount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
