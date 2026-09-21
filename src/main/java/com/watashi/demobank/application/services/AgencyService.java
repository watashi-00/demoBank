package com.watashi.demobank.application.services;

import com.watashi.demobank.domain.entities.Agency;
import com.watashi.demobank.domain.repositories.AgencyRepository;
import com.watashi.demobank.domain.repositories.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgencyService {

    private final AgencyRepository agencyRepository;
    private final BankRepository bankRepository;

    public AgencyService(AgencyRepository agencyRepository, BankRepository bankRepository) {
        this.agencyRepository = agencyRepository;
        this.bankRepository = bankRepository;
    }

    @Transactional(readOnly = true)
    public Agency findById(Long id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agency not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Agency> findAll() {
        return agencyRepository.findAll();
    }

    @Transactional
    public Agency createAgency(Long bankId, String code) {
        if (bankId == null) {
            throw new IllegalArgumentException("Bank ID cannot be null");
        }
        bankRepository.findById(bankId)
                .orElseThrow(() -> new IllegalArgumentException("Bank not found with id: " + bankId));

        Agency agency = new Agency(bankId, code);
        return agencyRepository.save(agency);
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id);
        agencyRepository.deleteById(id);
    }
}
