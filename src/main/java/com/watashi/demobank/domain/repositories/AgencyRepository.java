package com.watashi.demobank.domain.repositories;

import com.watashi.demobank.domain.entities.Agency;

import java.util.List;
import java.util.Optional;

public interface AgencyRepository {
    Optional<Agency>  findById(Long id);
    List<Agency>      findAll();
    Agency            save(Agency Agency);
    void              deleteById(Long id);
}
