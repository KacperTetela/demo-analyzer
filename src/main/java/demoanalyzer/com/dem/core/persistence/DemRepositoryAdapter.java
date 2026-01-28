package demoanalyzer.com.dem.core.persistence;

import demoanalyzer.com.dem.core.domain.model.Dem;
import demoanalyzer.com.dem.core.domain.repository.DemRepository;
import demoanalyzer.com.dem.core.persistence.mapper.DemPersistenceMapper;
import demoanalyzer.com.dem.core.persistence.repository.DemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DemRepositoryAdapter implements DemRepository {

  private final DemJpaRepository demJpaRepository;
  private final DemPersistenceMapper mapper;

  @Override
  public Dem save(Dem dem) {
    return mapper.toDomain(demJpaRepository.save(mapper.toEntity(dem)));
  }

  @Override
  public Optional<Dem> findById(Long id) {
    return demJpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public List<Dem> findAll(Long ownerId) {
    return demJpaRepository.findAllForOwner(ownerId).stream().map(mapper::toDomain).toList();
  }
}
