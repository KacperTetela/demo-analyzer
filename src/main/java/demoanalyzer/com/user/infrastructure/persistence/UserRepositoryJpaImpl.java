package demoanalyzer.com.user.infrastructure.persistence;

import demoanalyzer.com.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJpaImpl extends JpaRepository<UserEntity, Long>, UserRepository {

  @Override
  Optional<UserEntity> findByUsername(String username);

  @Override
  boolean existsByUsername(String username);
}
