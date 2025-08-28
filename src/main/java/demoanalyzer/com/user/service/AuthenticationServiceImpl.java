package demoanalyzer.com.user.service;

import demoanalyzer.com.domain.user.AuthenticationService;
import demoanalyzer.com.domain.user.User;
import demoanalyzer.com.exception.BadCredentialsException;
import demoanalyzer.com.exception.UsernameAlreadyExistsException;
import demoanalyzer.com.user.model.UserRequestDto;
import demoanalyzer.com.user.model.UserResponseDto;
import demoanalyzer.com.user.repository.UserEntity;
import demoanalyzer.com.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;

  public AuthenticationServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserResponseDto register(UserRequestDto userRequest) {
    User user = convertToUser(userRequest);
    User registeredUser = registerUser(user);
    return UserResponseDto.from(registeredUser);
  }

  @Override
  public User registerUser(User user) {
    if (userRepository.findByUsername(user.username()).isPresent()) {
      throw new UsernameAlreadyExistsException();
    }
    UserEntity userEntity = new UserEntity(user.username(), user.password());
    UserEntity savedEntity = userRepository.save(userEntity);
    return new User(savedEntity.getId(), savedEntity.getUsername(), savedEntity.getPassword());
  }

  public UserResponseDto login(UserRequestDto userRequest) {
    User user = loginUser(userRequest.username(), userRequest.password());
    // Tutaj w prawdziwej aplikacji wygenerowałbyś token JWT
    return new UserResponseDto(user.id(), user.username());
  }

  @Override
  public User loginUser(String username, String password) {
    UserEntity userEntity =
        userRepository.findByUsername(username).orElseThrow(BadCredentialsException::new);

    if (!userEntity.getPassword().equals(password)) {
      throw new BadCredentialsException();
    }
    return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());
  }

  @Override
  public void logoutUser(String accessToken) {
    // W prawdziwej aplikacji obsłużyłbyś unieważnienie tokenu
  }

  private User convertToUser(UserRequestDto userRequest) {
    return new User(null, userRequest.username(), userRequest.password());
  }
}
