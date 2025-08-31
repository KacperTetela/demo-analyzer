package demoanalyzer.com.user.service;

import demoanalyzer.com.user.domain.AuthenticationService;
import demoanalyzer.com.user.domain.User;
import demoanalyzer.com.user.domain.UserRepository;
import demoanalyzer.com.user.exception.BadCredentialsException;
import demoanalyzer.com.user.exception.UsernameAlreadyExistsException;
import demoanalyzer.com.security.TokenService;
import demoanalyzer.com.user.model.TokenResponseDto;
import demoanalyzer.com.user.model.UserRequestDto;
import demoanalyzer.com.user.model.UserResponseDto;
import demoanalyzer.com.user.infrastructure.persistence.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;


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
    String encodedPassword = passwordEncoder.encode(user.password()); // Hashowanie
    UserEntity userEntity = new UserEntity(user.username(), encodedPassword);
    UserEntity savedEntity = userRepository.save(userEntity);
    return new User(savedEntity.getId(), savedEntity.getUsername(), savedEntity.getPassword());
  }

  public TokenResponseDto login(UserRequestDto userRequest) {
    User user = loginUser(userRequest.username(), userRequest.password());
    String token = tokenService.generateToken(user.id());
    return new TokenResponseDto(token);
  }

  @Override
  public User loginUser(String username, String password) {
    UserEntity userEntity =
        userRepository.findByUsername(username).orElseThrow(BadCredentialsException::new);

    if (!passwordEncoder.matches(password, userEntity.getPassword())) { // Porównanie
      throw new BadCredentialsException();
    }
    return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());
  }

  @Override
  public void logoutUser(String accessToken) {
    // W tej prostej wersji, nie robimy nic
    // W pełnej wersji: tokenBlacklistService.blacklistToken(accessToken);
  }

  private User convertToUser(UserRequestDto userRequest) {
    return new User(null, userRequest.username(), userRequest.password());
  }
}
