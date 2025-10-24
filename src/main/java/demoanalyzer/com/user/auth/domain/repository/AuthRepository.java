package demoanalyzer.com.user.auth.domain.repository;

import demoanalyzer.com.user.auth.domain.model.User;

import java.util.Optional;

public interface AuthRepository {

  Optional<User> findUser(String email);

  boolean existsUser(String email);
}
