package demoanalyzer.com.dem.persistence;

import demoanalyzer.com.dem.domain.model.Dem;
import demoanalyzer.com.dem.domain.model.repository.DemRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DemRepositoryAdapter implements DemRepository {
  private final DemJpaRepository demJpaRepository;

  public DemRepositoryAdapter(DemJpaRepository demJpaRepository) {
    this.demJpaRepository = demJpaRepository;
  }

  @Override
  public Optional<Dem> findById(Long id) {
    return demJpaRepository.findById(id).map(DemEntity::toDomain);
  }
}
