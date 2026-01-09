package demoanalyzer.com.user.auth.persistence;

import demoanalyzer.com.user.auth.domain.model.AuthUser;
import demoanalyzer.com.user.auth.domain.repository.AuthRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthRepositoryAdapter implements AuthRepository {

  AuthJpaRepository authJpaRepository;

  public AuthRepositoryAdapter(AuthJpaRepository authJpaRepository) {
    this.authJpaRepository = authJpaRepository;
  }

  @Override
  public Optional<AuthUser> findUser(String email) {
    return authJpaRepository.findByEmail(email);
  }

  @Override
  public Optional<AuthUser> findUser(Long id) {
    return authJpaRepository.findById(id).map(AuthUserEntity::from);
  }

  @Override
  public boolean existsUser(String email) {
    return authJpaRepository.existsByEmail(email);
  }

  @Override
  public AuthUser saveUser(AuthUser authUser) {
    return AuthUserEntity.from(authJpaRepository.save(AuthUserEntity.from(authUser)));
  }

  @Override
  public void deleteUser(Long id) {
    authJpaRepository.deleteById(id);
  }

  @Override
  public void updatePasswordById(Long id, String newPassword) {
    authJpaRepository.updatePasswordById(id, newPassword);
  }

  @Override
  public void updateEmailById(Long id, String newEmail) {
    authJpaRepository.updateEmailById(id, newEmail);
  }
}
