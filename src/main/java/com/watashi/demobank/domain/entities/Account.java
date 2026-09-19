package com.watashi.demobank.domain.entities;

import com.watashi.demobank.domain.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.Instant;

public class Account {
    long    id;
    long    customerId;
    long    agencyId;

    String  email;
    String  accountNumber;

    BigDecimal      balance;
    AccountStatus   status;
    Transaction     transactions;

    Instant createdAt;
    Instant updatedAt;
}
