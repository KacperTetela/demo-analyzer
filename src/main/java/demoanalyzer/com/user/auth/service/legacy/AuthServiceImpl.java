package demoanalyzer.com.user.auth.service.legacy;

import demoanalyzer.com.user.auth.domain.model.AuthUser;
import demoanalyzer.com.user.auth.domain.repository.AuthRepository;
import demoanalyzer.com.user.auth.domain.service.AuthService;
import demoanalyzer.com.user.auth.exception.BadCredentialsException;
import demoanalyzer.com.user.auth.exception.UserNotFoundException;
import demoanalyzer.com.user.auth.persistence.AuthUserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final AuthRepository userRepositoryJpaImpl;
  private final PasswordEncoder passwordEncoder;

  public AuthServiceImpl(AuthRepository userRepositoryJpaImpl, PasswordEncoder passwordEncoder) {
    this.userRepositoryJpaImpl = userRepositoryJpaImpl;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public AuthUser updateProfileUser(String userId, AuthUser updatedUser) {
    AuthUserEntity authUserEntity =
        userRepositoryJpaImpl
            .findById(Long.valueOf(userId))
            .orElseThrow(
                () -> new UserNotFoundException("Nie znaleziono użytkownika o podanym ID."));

    // Aktualizuj nazwę użytkownika, jeśli podano
    if (updatedUser.email() != null && !updatedUser.email().isBlank()) {
      authUserEntity.setEmail(updatedUser.email());
    }

    // Aktualizuj hasło, jeśli podano, pamiętając o hashowaniu
    if (updatedUser.password() != null && !updatedUser.password().isBlank()) {
      authUserEntity.setPassword(passwordEncoder.encode(updatedUser.password()));
    }

    AuthUserEntity savedEntity = userRepositoryJpaImpl.save(authUserEntity);
    return new AuthUser(savedEntity.getId(), savedEntity.getEmail(), savedEntity.getPassword());
  }

  @Override
  public void changePasswordUser(String userId, String oldPassword, String newPassword) {
    AuthUserEntity authUserEntity =
        userRepositoryJpaImpl
            .findById(Long.valueOf(userId))
            .orElseThrow(
                () -> new UserNotFoundException("Nie znaleziono użytkownika o podanym ID."));

    // Weryfikacja starego hasła z wykorzystaniem PasswordEncoder
    if (!passwordEncoder.matches(oldPassword, authUserEntity.getPassword())) {
      throw new BadCredentialsException();
    }

    authUserEntity.setPassword(newPassword); // W prawdziwej aplikacji haszowanie nowego hasła!
    userRepositoryJpaImpl.save(authUserEntity);
  }


  @Override
  public void deleteAccountUser(String userId) {
    if (!userRepositoryJpaImpl.existsById(Long.valueOf(userId))) {
      throw new UserNotFoundException("Nie znaleziono użytkownika o podanym ID.");
    }
    userRepositoryJpaImpl.deleteById(Long.valueOf(userId));
  }
}
