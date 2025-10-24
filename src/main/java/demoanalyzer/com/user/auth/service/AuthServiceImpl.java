package demoanalyzer.com.user.auth.service;

import demoanalyzer.com.user.auth.domain.model.User;
import demoanalyzer.com.user.auth.domain.service.AuthService;
import demoanalyzer.com.user.auth.exception.BadCredentialsException;
import demoanalyzer.com.user.auth.exception.UserNotFoundException;
import demoanalyzer.com.user.auth.model.ChangePasswordRequestDto;
import demoanalyzer.com.user.auth.model.UserRequestDto;
import demoanalyzer.com.user.auth.model.UserResponseDto;
import demoanalyzer.com.user.auth.persistence.AuthUserEntity;
import demoanalyzer.com.user.auth.persistence.AuthRepository;
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

  public UserResponseDto updateProfile(String userId, UserRequestDto userRequest) {
    User updatedUser =
        updateProfileUser(
            userId, new User(Long.valueOf(userId), userRequest.username(), userRequest.password()));
    return UserResponseDto.from(updatedUser);
  }

  @Override
  public User updateProfileUser(String userId, User updatedUser) {
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
    return new User(savedEntity.getId(), savedEntity.getEmail(), savedEntity.getPassword());
  }

  public void changePassword(String userId, ChangePasswordRequestDto changePasswordRequest) {
    changePasswordUser(
        userId, changePasswordRequest.oldPassword(), changePasswordRequest.newPassword());
  }

  @Override
  public User registerUser(User user) {
    return null;
  }

  @Override
  public User loginUser(String email, String password) {
    return null;
  }

  @Override
  public void logoutUser(String accessToken) {

  }

  @Override
  public void changeUserEmail(String userId, String oldPassword, String newPassword) {

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

  public void deleteAccount(String userId) {
    deleteAccountUser(userId);
  }

  @Override
  public void deleteAccountUser(String userId) {
    if (!userRepositoryJpaImpl.existsById(Long.valueOf(userId))) {
      throw new UserNotFoundException("Nie znaleziono użytkownika o podanym ID.");
    }
    userRepositoryJpaImpl.deleteById(Long.valueOf(userId));
  }
}
