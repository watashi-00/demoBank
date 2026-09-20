package com.watashi.demobank.domain.entities;

import com.watashi.demobank.domain.enums.AccountStatus;
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
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private Long agencyId;

    private String accountNumber;
    private String email;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    public Account(Long customerId, Long agencyId, String accountNumber, String email) {
        this.customerId = customerId;
        this.agencyId = agencyId;
        this.accountNumber = accountNumber;
        this.email = email;
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.ACTIVE;
        validate();
    }

    public void validate() {
        if (this.customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (this.agencyId == null) {
            throw new IllegalArgumentException("Agency ID cannot be null");
        }
        if (this.accountNumber == null || this.accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        if (this.email == null || this.email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }

    public boolean isActive() {
        return AccountStatus.ACTIVE.equals(this.status);
    }

    public boolean hasSufficientBalance(BigDecimal amount) {
        return this.balance != null && amount != null && this.balance.compareTo(amount) >= 0;
    }

    public void deposit(BigDecimal amount) {
        if (!isActive()) {
            throw new IllegalStateException("Cannot deposit into an inactive account");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (!isActive()) {
            throw new IllegalStateException("Cannot withdraw from an inactive account");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (!hasSufficientBalance(amount)) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
    }
}
