package demoanalyzer.com.dem.domain.repository;

import demoanalyzer.com.dem.domain.model.Dem;

import java.util.List;
import java.util.Optional;

public interface DemRepository {
  Dem save(Dem dem);

  Optional<Dem> findById(Long id);

  List<Dem> findAll(Long ownerId);
}
