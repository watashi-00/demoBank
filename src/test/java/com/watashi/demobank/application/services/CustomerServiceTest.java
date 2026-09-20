package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Customer;
import com.watashi.demobank.domain.repositories.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("Should create customer successfully")
    void createCustomer_WithValidCpf_ShouldSaveAndReturnCustomer() {
        String cpf = "12345678901";
        Customer expectedCustomer = new Customer(cpf);
        expectedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);

        Customer createdCustomer = customerService.createCustomer(cpf);

        assertNotNull(createdCustomer);
        assertEquals(1L, createdCustomer.getId());
        assertEquals(cpf, createdCustomer.getCpf());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should return customer when ID exists")
    void findById_WhenCustomerExists_ShouldReturnCustomer() {
        Long customerId = 1L;
        Customer expectedCustomer = new Customer("12345678901");
        expectedCustomer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

        Customer actualCustomer = customerService.findById(customerId);

        assertNotNull(actualCustomer);
        assertEquals(customerId, actualCustomer.getId());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when customer ID does not exist")
    void findById_WhenCustomerNotFound_ShouldThrowException() {
        Long customerId = 999L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> customerService.findById(customerId)
        );

        assertEquals("Customer not found with id: 999", exception.getMessage());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("Should delete customer when ID exists")
    void deleteById_WhenCustomerExists_ShouldDeleteCustomer() {
        Long customerId = 1L;
        Customer customer = new Customer("12345678901");
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).deleteById(customerId);

        customerService.deleteById(customerId);

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    @DisplayName("Should throw exception and not call deleteById when customer does not exist")
    void deleteById_WhenCustomerNotFound_ShouldThrowExceptionAndNotDelete() {
        Long customerId = 999L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.deleteById(customerId)
        );

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).deleteById(anyLong());
    }
}
