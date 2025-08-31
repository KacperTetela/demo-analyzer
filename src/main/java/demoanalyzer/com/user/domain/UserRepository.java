package demoanalyzer.com.user.domain;

import demoanalyzer.com.user.infrastructure.persistence.UserEntity;

import java.util.Optional;

public interface UserRepository {
    //do zmiany nie mzoe uzywac Entity w domenie
  Optional<UserEntity> findByUsername(String username);

  boolean existsByUsername(String username);

}
