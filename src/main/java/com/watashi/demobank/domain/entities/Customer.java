package com.watashi.demobank.domain.entities;

import java.time.Instant;
import java.util.ArrayList;

public class Customer {
    Long    id;
    String  cpf;
    ArrayList<Account> accounts;

    Instant createdAt;
    Instant updatedAt;
}