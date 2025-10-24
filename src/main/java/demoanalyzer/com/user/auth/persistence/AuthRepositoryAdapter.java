package demoanalyzer.com.user.auth.persistence;

import demoanalyzer.com.user.auth.domain.repository.AuthRepository;
import demoanalyzer.com.user.auth.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthRepositoryAdapter implements AuthRepository {

  AuthJpaRepository authJpaRepository;

  public AuthRepositoryAdapter(AuthJpaRepository authJpaRepository) {
    this.authJpaRepository = authJpaRepository;
  }

  @Override
  public Optional<User> findUser(String email) {
    return authJpaRepository.findByEmail(email);
  }

  @Override
  public boolean existsUser(String email) {
    return existsUser(email);
  }
}
