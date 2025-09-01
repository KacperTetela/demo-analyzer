package demoanalyzer.com.user.domain;

import java.util.Optional;

public interface UserRepository {

  Optional<User> findByUsernamePort(String username);

  boolean existsByUsernamePort(String username);
}
