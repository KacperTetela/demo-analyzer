package demoanalyzer.com.user.auth.domain.repository;

import demoanalyzer.com.user.auth.domain.model.AuthUser;

import java.util.Optional;

public interface AuthRepository {

  Optional<AuthUser> findUser(String email);

  boolean existsUser(String email);

  AuthUser saveUser(AuthUser authUser);
}
