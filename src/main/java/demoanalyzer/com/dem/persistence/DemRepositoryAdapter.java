package demoanalyzer.com.dem.persistence;

import demoanalyzer.com.dem.domain.model.Dem;
import demoanalyzer.com.dem.domain.repository.DemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DemRepositoryAdapter implements DemRepository {

  private final DemJpaRepository demJpaRepository;

  @Override
  public Dem save(Dem dem) {
    return DemEntity.from(demJpaRepository.save(DemEntity.from(dem)));
    //AuthUserEntity.from(authJpaRepository.save(AuthUserEntity.from(authUser)));
  }

  @Override
  public Optional<Dem> findById(Long id) {
    return Optional.empty();
  }
}
