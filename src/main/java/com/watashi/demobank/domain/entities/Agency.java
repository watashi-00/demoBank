package com.watashi.demobank.domain.entities;

import java.time.Instant;

public class Agency {
    long id;
    long bankId;

    String code;

    Instant createdAt;
    Instant updatedAt;
}
