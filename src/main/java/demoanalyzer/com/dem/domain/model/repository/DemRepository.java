package demoanalyzer.com.dem.domain.model.repository;

import demoanalyzer.com.dem.domain.model.Dem;

import java.util.Optional;

public interface DemRepository {
  Optional<Dem> findById(Long id);
}
