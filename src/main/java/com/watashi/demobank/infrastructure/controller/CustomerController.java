package com.watashi.demobank.infrastructure.controller;

import com.watashi.demobank.application.services.CustomerService;
import com.watashi.demobank.domain.entities.Customer;
import com.watashi.demobank.infrastructure.controller.dto.CreateCustomerRequest;
import com.watashi.demobank.infrastructure.controller.dto.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Customer customer = service.findById(id);
        return ResponseEntity.ok(CustomerResponse.fromEntity(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        Customer createdCustomer = service.createCustomer(request.cpf());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCustomer.getId())
                .toUri();
        return ResponseEntity.created(location).body(CustomerResponse.fromEntity(createdCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
