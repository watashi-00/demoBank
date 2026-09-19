package com.watashi.demobank.infrastructure.repository.customer;

import com.watashi.demobank.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCustomerRepository extends JpaRepository<Customer, Long> {
}
