package com.watashi.demobank.infrastructure.controller;

import com.watashi.demobank.application.services.CustomerService;
import com.watashi.demobank.domain.entities.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping     // todo: debug route, remove after
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.notFound().build();
    }


}
