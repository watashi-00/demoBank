package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Bank;
import com.watashi.demobank.domain.repositories.BankRepository;
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
class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @Test
    @DisplayName("Should create bank successfully")
    void createBank_WithValidData_ShouldSaveAndReturnBank() {
        String name = "DemoBank Central";
        String number = "001";
        Bank expectedBank = new Bank(name, number);
        expectedBank.setId(1L);

        when(bankRepository.save(any(Bank.class))).thenReturn(expectedBank);

        Bank createdBank = bankService.createBank(name, number);

        assertNotNull(createdBank);
        assertEquals(1L, createdBank.getId());
        assertEquals(name, createdBank.getName());
        assertEquals(number, createdBank.getNumber());
        verify(bankRepository, times(1)).save(any(Bank.class));
    }

    @Test
    @DisplayName("Should return bank when ID exists")
    void findById_WhenBankExists_ShouldReturnBank() {
        Long bankId = 1L;
        Bank expectedBank = new Bank("DemoBank Retail", "002");
        expectedBank.setId(bankId);

        when(bankRepository.findById(bankId)).thenReturn(Optional.of(expectedBank));

        Bank actualBank = bankService.findById(bankId);

        assertNotNull(actualBank);
        assertEquals(bankId, actualBank.getId());
        assertEquals("DemoBank Retail", actualBank.getName());
        verify(bankRepository, times(1)).findById(bankId);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when bank ID does not exist")
    void findById_WhenBankNotFound_ShouldThrowException() {
        Long bankId = 999L;

        when(bankRepository.findById(bankId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bankService.findById(bankId)
        );

        assertEquals("Bank not found with id: 999", exception.getMessage());
        verify(bankRepository, times(1)).findById(bankId);
    }

    @Test
    @DisplayName("Should return list of all banks")
    void findAll_ShouldReturnAllBanks() {
        Bank b1 = new Bank("DemoBank 1", "001");
        Bank b2 = new Bank("DemoBank 2", "002");

        when(bankRepository.findAll()).thenReturn(List.of(b1, b2));

        List<Bank> banks = bankService.findAll();

        assertEquals(2, banks.size());
        verify(bankRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should delete bank when ID exists")
    void deleteById_WhenBankExists_ShouldDeleteBank() {
        Long bankId = 1L;
        Bank bank = new Bank("DemoBank Digital", "003");
        bank.setId(bankId);

        when(bankRepository.findById(bankId)).thenReturn(Optional.of(bank));
        doNothing().when(bankRepository).deleteById(bankId);

        bankService.deleteById(bankId);

        verify(bankRepository, times(1)).findById(bankId);
        verify(bankRepository, times(1)).deleteById(bankId);
    }

    @Test
    @DisplayName("Should throw exception and not call deleteById when bank does not exist")
    void deleteById_WhenBankNotFound_ShouldThrowExceptionAndNotDelete() {
        Long bankId = 999L;

        when(bankRepository.findById(bankId)).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> bankService.deleteById(bankId)
        );

        verify(bankRepository, times(1)).findById(bankId);
        verify(bankRepository, never()).deleteById(anyLong());
    }
}
