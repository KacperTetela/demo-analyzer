package demoanalyzer.com.dem.core.domain.repository;

import demoanalyzer.com.dem.core.domain.model.Dem;

import java.util.List;
import java.util.Optional;

public interface DemRepository {
  Dem save(Dem dem);

  Optional<Dem> findById(Long id);

  List<Dem> findAll(Long ownerId);
}
