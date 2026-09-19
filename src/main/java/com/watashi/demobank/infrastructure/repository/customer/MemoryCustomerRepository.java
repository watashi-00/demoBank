package com.watashi.demobank.infrastructure.repository.customer;

import com.watashi.demobank.domain.entities.Customer;
import com.watashi.demobank.domain.repositories.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("memory")
public class MemoryCustomerRepository implements CustomerRepository {

    private final List<Customer> customers = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public Optional<Customer> findById(Long id) {
        for (Customer customer : customers) {
            if (Objects.equals(customer.getId(), id)) {
                return Optional.of(customer);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

    @Override
    public Customer save(Customer customer) {
        Instant now = Instant.now();
        if (customer.getId() == null) {
            customer.setId(idSequence.getAndIncrement());
            if (customer.getCreatedAt() == null) {
                customer.setCreatedAt(now);
            }
            customer.setUpdatedAt(now);
            customers.add(customer);
        } else {
            customer.setUpdatedAt(now);
            deleteById(customer.getId());
            customers.add(customer);
        }
        return customer;
    }

    @Override
    public void deleteById(Long id) {
        customers.removeIf(customer -> Objects.equals(customer.getId(), id));
    }
}
