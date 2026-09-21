package com.watashi.demobank.domain.entities;

import com.watashi.demobank.domain.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    private Long fromAccountId;
    private Long toAccountId;

    private String notes;

    private Instant createdAt;

    public Transaction(TransactionType type, BigDecimal amount, Long fromAccountId, Long toAccountId, String notes) {
        this.type = type;
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.notes = notes;
        this.createdAt = Instant.now();
        validate();
    }

    public void validate() {
        if (this.type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        if (this.amount == null || this.amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero");
        }
        if (this.type == TransactionType.DEPOSIT && this.toAccountId == null) {
            throw new IllegalArgumentException("Target account (toAccountId) is required for deposit");
        }
        if (this.type == TransactionType.WITHDRAW && this.fromAccountId == null) {
            throw new IllegalArgumentException("Source account (fromAccountId) is required for withdrawal");
        }
        if (this.type == TransactionType.TRANSFER) {
            if (this.fromAccountId == null || this.toAccountId == null) {
                throw new IllegalArgumentException("Both source and target accounts are required for transfer");
            }
            if (this.fromAccountId.equals(this.toAccountId)) {
                throw new IllegalArgumentException("Source and target accounts must be different for transfer");
            }
        }
    }
}
