package demoanalyzer.com.user.service;

import demoanalyzer.com.domain.user.User;
import demoanalyzer.com.domain.user.UserProfileService;
import demoanalyzer.com.exception.BadCredentialsException;
import demoanalyzer.com.exception.UserNotFoundException;
import demoanalyzer.com.user.model.ChangePasswordRequestDto;
import demoanalyzer.com.user.model.UserRequestDto;
import demoanalyzer.com.user.model.UserResponseDto;
import demoanalyzer.com.user.repository.UserEntity;
import demoanalyzer.com.user.repository.UserRepository;

public class UserProfileServiceImpl implements UserProfileService {

  private final UserRepository userRepository;

  public UserProfileServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
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
        userRepository
            .findById(Long.valueOf(userId))
            .orElseThrow(
                () -> new UserNotFoundException("Nie znaleziono użytkownika o podanym ID."));

    userEntity.setUsername(updatedUser.username());
    userEntity.setPassword(updatedUser.password()); // W prawdziwej aplikacji hasłowanie!

    UserEntity savedEntity = userRepository.save(userEntity);
    return new User(savedEntity.getId(), savedEntity.getUsername(), savedEntity.getPassword());
  }

  public void changePassword(String userId, ChangePasswordRequestDto changePasswordRequest) {
    changePasswordUser(
        userId, changePasswordRequest.oldPassword(), changePasswordRequest.newPassword());
  }

  @Override
  public void changePasswordUser(String userId, String oldPassword, String newPassword) {
    UserEntity userEntity =
        userRepository
            .findById(Long.valueOf(userId))
            .orElseThrow(
                () -> new UserNotFoundException("Nie znaleziono użytkownika o podanym ID."));

    if (!userEntity.getPassword().equals(oldPassword)) { // Pamiętaj o hashowaniu
      throw new BadCredentialsException();
    }

    userEntity.setPassword(newPassword); // W prawdziwej aplikacji haszowanie nowego hasła!
    userRepository.save(userEntity);
  }

  public void deleteAccount(String userId) {
    deleteAccountUser(userId);
  }

  @Override
  public void deleteAccountUser(String userId) {
    if (!userRepository.existsById(Long.valueOf(userId))) {
      throw new UserNotFoundException("Nie znaleziono użytkownika o podanym ID.");
    }
    userRepository.deleteById(Long.valueOf(userId));
  }
}
