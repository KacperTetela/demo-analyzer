package demoanalyzer.com.user.infrastructure.persistence;

import demoanalyzer.com.user.domain.User;
import demoanalyzer.com.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public abstract class UserRepositoryJpaImpl
    implements JpaRepository<UserEntity, Long>, UserRepository {

  abstract UserEntity findByUsername(String username);

  abstract boolean existsByUsername(String username);

  @Override
  public Optional<User> findByUsernamePort(String username) {
    return Optional.ofNullable(findByUsername(username)).map(this::toUser);
  }

  @Override
  public boolean existsByUsernamePort(String username) {
    return existsByUsername(username);
  }

  private User toUser(UserEntity userEntity) {
    return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());
  }
}
