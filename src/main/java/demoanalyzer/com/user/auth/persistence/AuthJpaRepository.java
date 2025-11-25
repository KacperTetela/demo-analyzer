package demoanalyzer.com.user.auth.persistence;

import demoanalyzer.com.user.auth.domain.model.AuthUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthJpaRepository extends JpaRepository<AuthUserEntity, Long> {

  Optional<AuthUser> findByEmail(String email);

  Optional<AuthUserEntity> findById(Long id);

  boolean existsByEmail(String email);

  void deleteById(Long aLong);

  @Modifying
  @Query("UPDATE AuthUserEntity u SET u.password = :password WHERE u.id = :id")
  void updatePasswordById(@Param("id") Long id, @Param("password") String password);

  @Modifying
  @Query("UPDATE AuthUserEntity u SET u.email = :email WHERE u.id = :id")
  void updateEmailById(@Param("id") Long id, @Param("email") String email);
}
