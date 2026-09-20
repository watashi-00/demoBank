package com.watashi.demobank.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "banks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String number;

    private Instant createdAt;
    private Instant updatedAt;

    public Bank(String name, String number) {
        this.name = name;
        this.number = number;
        this.createdAt = Instant.now();
        validate();
    }

    public void validate() {
        if (this.name == null || this.name.isBlank()) {
            throw new IllegalArgumentException("Bank name cannot be null or empty");
        }
        if (this.number == null || this.number.isBlank()) {
            throw new IllegalArgumentException("Bank number cannot be null or empty");
        }
        if (!this.number.matches("\\d{3}")) {
            throw new IllegalArgumentException("Bank number must be exactly 3 digits");
        }
    }
}
