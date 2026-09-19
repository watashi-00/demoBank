package com.watashi.demobank.infrastructure.repository.agency;

import com.watashi.demobank.domain.entities.Agency;
import com.watashi.demobank.domain.repositories.AgencyRepository;
import com.watashi.demobank.infrastructure.repository.common.AbstractMemoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("memory")
public class MemoryAgencyRepository extends AbstractMemoryRepository<Agency> implements AgencyRepository {

    public MemoryAgencyRepository() {
        super(
                Agency::getId,
                Agency::setId,
                Agency::getCreatedAt,
                Agency::setCreatedAt,
                Agency::setUpdatedAt
        );
    }
}
