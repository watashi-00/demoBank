package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Customer;
import com.watashi.demobank.domain.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements CustomerRepository {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository customerRepository) {
        this.repository = customerRepository;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
