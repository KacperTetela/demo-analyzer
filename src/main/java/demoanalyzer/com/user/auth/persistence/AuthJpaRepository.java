package demoanalyzer.com.user.auth.persistence;

import demoanalyzer.com.user.auth.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthJpaRepository extends JpaRepository<AuthUserEntity, Long> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}
