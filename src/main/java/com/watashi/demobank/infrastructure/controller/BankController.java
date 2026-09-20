package com.watashi.demobank.infrastructure.controller;

import com.watashi.demobank.application.services.BankService;
import com.watashi.demobank.domain.entities.Bank;
import com.watashi.demobank.infrastructure.controller.dto.BankResponse;
import com.watashi.demobank.infrastructure.controller.dto.CreateBankRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    private final BankService service;

    public BankController(BankService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankResponse> getBankById(@PathVariable Long id) {
        Bank bank = service.findById(id);
        return ResponseEntity.ok(BankResponse.fromEntity(bank));
    }

    @GetMapping
    public ResponseEntity<List<BankResponse>> getAllBanks() {
        List<BankResponse> banks = service.findAll()
                .stream()
                .map(BankResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(banks);
    }

    @PostMapping
    public ResponseEntity<BankResponse> createBank(@Valid @RequestBody CreateBankRequest request) {
        Bank createdBank = service.createBank(request.name(), request.number());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBank.getId())
                .toUri();
        return ResponseEntity.created(location).body(BankResponse.fromEntity(createdBank));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
