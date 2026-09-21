package com.watashi.demobank.infrastructure.controller;

import com.watashi.demobank.application.services.AgencyService;
import com.watashi.demobank.domain.entities.Agency;
import com.watashi.demobank.infrastructure.controller.dto.AgencyResponse;
import com.watashi.demobank.infrastructure.controller.dto.CreateAgencyRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/agencies")
public class AgencyController {

    private final AgencyService service;

    public AgencyController(AgencyService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyResponse> getAgencyById(@PathVariable Long id) {
        Agency agency = service.findById(id);
        return ResponseEntity.ok(AgencyResponse.fromEntity(agency));
    }

    @GetMapping
    public ResponseEntity<List<AgencyResponse>> getAllAgencies() {
        List<AgencyResponse> agencies = service.findAll()
                .stream()
                .map(AgencyResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(agencies);
    }

    @PostMapping
    public ResponseEntity<AgencyResponse> createAgency(@Valid @RequestBody CreateAgencyRequest request) {
        Agency createdAgency = service.createAgency(request.bankId(), request.code());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAgency.getId())
                .toUri();
        return ResponseEntity.created(location).body(AgencyResponse.fromEntity(createdAgency));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
