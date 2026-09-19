package com.watashi.demobank.domain.entities;

import com.watashi.demobank.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public class Transaction {
    long id;

    long    fromAccountId;
    long    toAccountId;

    TransactionType type;
    BigDecimal      amount;
    String          notes;

    Instant createdAt;
    Instant updatedAt;
}
