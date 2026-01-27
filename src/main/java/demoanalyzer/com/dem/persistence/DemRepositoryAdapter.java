package demoanalyzer.com.dem.persistence;

import demoanalyzer.com.dem.domain.model.Dem;
import demoanalyzer.com.dem.domain.repository.DemRepository;
import demoanalyzer.com.dem.persistence.entity.DemEntity;
import demoanalyzer.com.dem.persistence.mapper.DemPersistenceMapper;
import demoanalyzer.com.dem.persistence.repository.DemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DemRepositoryAdapter implements DemRepository {

  private final DemJpaRepository demJpaRepository;
  private final DemPersistenceMapper mapper;

  @Override
  public Dem save(Dem dem) {
    DemEntity savedEntity = demJpaRepository.save(mapper.toEntity(dem));
    return mapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Dem> findById(Long id) {
    return demJpaRepository.findById(id).map(mapper::toDomain);
  }
}
