package com.watashi.demobank.infrastructure.repository.customer;

import com.watashi.demobank.domain.entities.Customer;
import com.watashi.demobank.domain.repositories.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("memory")
public class MemoryCustomerRepository implements CustomerRepository {
    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        return List.of();
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
