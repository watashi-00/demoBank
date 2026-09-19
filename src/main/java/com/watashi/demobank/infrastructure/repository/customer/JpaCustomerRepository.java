package com.watashi.demobank.infrastructure.repository.customer;

import com.watashi.demobank.domain.entities.Customer;
import com.watashi.demobank.domain.repositories.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaCustomerRepository implements CustomerRepository {

    private final SpringDataCustomerRepository springDataCustomerRepository;

    public JpaCustomerRepository(SpringDataCustomerRepository springDataCustomerRepository) {
        this.springDataCustomerRepository = springDataCustomerRepository;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return springDataCustomerRepository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return springDataCustomerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return springDataCustomerRepository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        springDataCustomerRepository.deleteById(id);
    }
}
