package com.watashi.demobank.infrastructure.repository.agency;

import com.watashi.demobank.domain.entities.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataAgencyRepository extends JpaRepository<Agency,Long> {
}
