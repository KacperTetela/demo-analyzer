package demoanalyzer.com.user.service;

import demoanalyzer.com.user.domain.User;
import demoanalyzer.com.user.domain.UserProfileService;
import demoanalyzer.com.user.exception.BadCredentialsException;
import demoanalyzer.com.user.exception.UserNotFoundException;
import demoanalyzer.com.user.model.ChangePasswordRequestDto;
import demoanalyzer.com.user.model.UserRequestDto;
import demoanalyzer.com.user.model.UserResponseDto;
import demoanalyzer.com.user.infrastructure.persistence.UserEntity;
import demoanalyzer.com.user.infrastructure.persistence.UserRepositoryJpaImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

  private final UserRepositoryJpaImpl userRepositoryJpaImpl;
  private final PasswordEncoder passwordEncoder;

  public UserProfileServiceImpl(UserRepositoryJpaImpl userRepositoryJpaImpl, PasswordEncoder passwordEncoder) {
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
    UserEntity userEntity =
        userRepositoryJpaImpl
            .findById(Long.valueOf(userId))
            .orElseThrow(
                () -> new UserNotFoundException("Nie znaleziono użytkownika o podanym ID."));

    // Aktualizuj nazwę użytkownika, jeśli podano
    if (updatedUser.username() != null && !updatedUser.username().isBlank()) {
      userEntity.setUsername(updatedUser.username());
    }

    // Aktualizuj hasło, jeśli podano, pamiętając o hashowaniu
    if (updatedUser.password() != null && !updatedUser.password().isBlank()) {
      userEntity.setPassword(passwordEncoder.encode(updatedUser.password()));
    }

    UserEntity savedEntity = userRepositoryJpaImpl.save(userEntity);
    return new User(savedEntity.getId(), savedEntity.getUsername(), savedEntity.getPassword());
  }

  public void changePassword(String userId, ChangePasswordRequestDto changePasswordRequest) {
    changePasswordUser(
        userId, changePasswordRequest.oldPassword(), changePasswordRequest.newPassword());
  }

  @Override
  public void changePasswordUser(String userId, String oldPassword, String newPassword) {
    UserEntity userEntity =
        userRepositoryJpaImpl
            .findById(Long.valueOf(userId))
            .orElseThrow(
                () -> new UserNotFoundException("Nie znaleziono użytkownika o podanym ID."));

    // Weryfikacja starego hasła z wykorzystaniem PasswordEncoder
    if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
      throw new BadCredentialsException();
    }

    userEntity.setPassword(newPassword); // W prawdziwej aplikacji haszowanie nowego hasła!
    userRepositoryJpaImpl.save(userEntity);
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
