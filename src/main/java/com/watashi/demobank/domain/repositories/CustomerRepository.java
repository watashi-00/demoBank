package com.watashi.demobank.domain.repositories;

import com.watashi.demobank.domain.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer>  findById(Long id);
    List<Customer>      findAll();
    Customer            save(Customer customer);
    void                deleteById(Long id);

}
