package com.watashi.demobank.infrastructure.repository.customer;

import com.watashi.demobank.domain.entities.Customer;
import com.watashi.demobank.domain.repositories.CustomerRepository;
import com.watashi.demobank.infrastructure.repository.common.AbstractMemoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("memory")
public class MemoryCustomerRepository extends AbstractMemoryRepository<Customer> implements CustomerRepository {

    public MemoryCustomerRepository() {
        super(
                Customer::getId,
                Customer::setId,
                Customer::getCreatedAt,
                Customer::setCreatedAt,
                Customer::setUpdatedAt
        );
    }
}
