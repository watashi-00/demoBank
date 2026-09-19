package com.watashi.demobank.infrastructure.repository.agency;

import com.watashi.demobank.domain.entities.Agency;
import com.watashi.demobank.domain.repositories.AgencyRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaAgencyRepository implements AgencyRepository {

    private final SpringDataAgencyRepository springDataAgencyRepository;

    public JpaAgencyRepository(SpringDataAgencyRepository springDataAgencyRepository) {
        this.springDataAgencyRepository = springDataAgencyRepository;
    }

    @Override
    public Optional<Agency> findById(Long id) {
        return springDataAgencyRepository.findById(id);
    }

    @Override
    public List<Agency> findAll() {
        return springDataAgencyRepository.findAll();
    }

    @Override
    public Agency save(Agency Agency) {
        return springDataAgencyRepository.save(Agency);
    }

    @Override
    public void deleteById(Long id) {
        springDataAgencyRepository.deleteById(id);
    }
}
