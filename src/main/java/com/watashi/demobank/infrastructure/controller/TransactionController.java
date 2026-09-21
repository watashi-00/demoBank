package com.watashi.demobank.infrastructure.controller;

import com.watashi.demobank.application.services.TransactionService;
import com.watashi.demobank.domain.entities.Transaction;
import com.watashi.demobank.infrastructure.controller.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody DepositRequest request) {
        Transaction transaction = service.deposit(request.accountId(), request.amount(), request.notes());
        URI location = buildLocationUri(transaction.getId());
        return ResponseEntity.created(location).body(TransactionResponse.fromEntity(transaction));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody WithdrawRequest request) {
        Transaction transaction = service.withdraw(request.accountId(), request.amount(), request.notes());
        URI location = buildLocationUri(transaction.getId());
        return ResponseEntity.created(location).body(TransactionResponse.fromEntity(transaction));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        Transaction transaction = service.transfer(request.fromAccountId(), request.toAccountId(), request.amount(), request.notes());
        URI location = buildLocationUri(transaction.getId());
        return ResponseEntity.created(location).body(TransactionResponse.fromEntity(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        Transaction transaction = service.findById(id);
        return ResponseEntity.ok(TransactionResponse.fromEntity(transaction));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(@RequestParam(required = false) Long accountId) {
        List<Transaction> transactions;
        if (accountId != null) {
            transactions = service.findByAccountId(accountId);
        } else {
            transactions = service.findAll();
        }
        List<TransactionResponse> response = transactions.stream()
                .map(TransactionResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    private URI buildLocationUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/transactions/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
