package demoanalyzer.com.user.auth.domain.repository;

import demoanalyzer.com.user.auth.domain.model.AuthUser;

import java.util.Optional;

public interface AuthRepository {

  Optional<AuthUser> findUser(String email);

  Optional<AuthUser> findUser(Long id);

  boolean existsUser(String email);

  AuthUser saveUser(AuthUser authUser);

  void deleteUser(Long id);
}
