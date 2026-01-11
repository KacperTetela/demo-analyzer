package demoanalyzer.com.dem.persistence;

import demoanalyzer.com.dem.domain.model.repository.DemRepository;
import demoanalyzer.com.user.auth.persistence.AuthJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class DemRepositoryAdapter implements DemRepository {
  private final AuthJpaRepository authJpaRepository;

  public DemRepositoryAdapter(AuthJpaRepository authJpaRepository) {
    this.authJpaRepository = authJpaRepository;
  }
}
