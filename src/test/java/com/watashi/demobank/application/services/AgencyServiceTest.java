package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Agency;
import com.watashi.demobank.domain.entities.Bank;
import com.watashi.demobank.domain.repositories.AgencyRepository;
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
class AgencyServiceTest {

    @Mock
    private AgencyRepository agencyRepository;

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private AgencyService agencyService;

    @Test
    @DisplayName("Should create agency successfully when bank exists")
    void createAgency_WithValidBank_ShouldSaveAndReturnAgency() {
        Long bankId = 1L;
        String code = "0001";
        Bank bank = new Bank("DemoBank", "001");
        bank.setId(bankId);

        Agency expectedAgency = new Agency(bankId, code);
        expectedAgency.setId(10L);

        when(bankRepository.findById(bankId)).thenReturn(Optional.of(bank));
        when(agencyRepository.save(any(Agency.class))).thenReturn(expectedAgency);

        Agency createdAgency = agencyService.createAgency(bankId, code);

        assertNotNull(createdAgency);
        assertEquals(10L, createdAgency.getId());
        assertEquals(bankId, createdAgency.getBankId());
        assertEquals(code, createdAgency.getCode());
        verify(bankRepository, times(1)).findById(bankId);
        verify(agencyRepository, times(1)).save(any(Agency.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating agency for non-existent bank")
    void createAgency_WhenBankDoesNotExist_ShouldThrowException() {
        Long bankId = 999L;

        when(bankRepository.findById(bankId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> agencyService.createAgency(bankId, "0001")
        );

        assertEquals("Bank not found with id: 999", exception.getMessage());
        verify(bankRepository, times(1)).findById(bankId);
        verify(agencyRepository, never()).save(any(Agency.class));
    }

    @Test
    @DisplayName("Should return agency when ID exists")
    void findById_WhenAgencyExists_ShouldReturnAgency() {
        Long agencyId = 10L;
        Agency expectedAgency = new Agency(1L, "0001");
        expectedAgency.setId(agencyId);

        when(agencyRepository.findById(agencyId)).thenReturn(Optional.of(expectedAgency));

        Agency actualAgency = agencyService.findById(agencyId);

        assertNotNull(actualAgency);
        assertEquals(agencyId, actualAgency.getId());
        assertEquals("0001", actualAgency.getCode());
        verify(agencyRepository, times(1)).findById(agencyId);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when agency ID does not exist")
    void findById_WhenAgencyNotFound_ShouldThrowException() {
        Long agencyId = 999L;

        when(agencyRepository.findById(agencyId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> agencyService.findById(agencyId)
        );

        assertEquals("Agency not found with id: 999", exception.getMessage());
        verify(agencyRepository, times(1)).findById(agencyId);
    }

    @Test
    @DisplayName("Should return list of all agencies")
    void findAll_ShouldReturnAllAgencies() {
        Agency a1 = new Agency(1L, "0001");
        Agency a2 = new Agency(1L, "0002");

        when(agencyRepository.findAll()).thenReturn(List.of(a1, a2));

        List<Agency> agencies = agencyService.findAll();

        assertEquals(2, agencies.size());
        verify(agencyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should delete agency when ID exists")
    void deleteById_WhenAgencyExists_ShouldDeleteAgency() {
        Long agencyId = 10L;
        Agency agency = new Agency(1L, "0001");
        agency.setId(agencyId);

        when(agencyRepository.findById(agencyId)).thenReturn(Optional.of(agency));
        doNothing().when(agencyRepository).deleteById(agencyId);

        agencyService.deleteById(agencyId);

        verify(agencyRepository, times(1)).findById(agencyId);
        verify(agencyRepository, times(1)).deleteById(agencyId);
    }

    @Test
    @DisplayName("Should throw exception and not call deleteById when agency does not exist")
    void deleteById_WhenAgencyNotFound_ShouldThrowExceptionAndNotDelete() {
        Long agencyId = 999L;

        when(agencyRepository.findById(agencyId)).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> agencyService.deleteById(agencyId)
        );

        verify(agencyRepository, times(1)).findById(agencyId);
        verify(agencyRepository, never()).deleteById(anyLong());
    }
}
