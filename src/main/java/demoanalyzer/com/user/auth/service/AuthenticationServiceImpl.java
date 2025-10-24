package demoanalyzer.com.user.auth.service;

import demoanalyzer.com.user.auth.persistence.AuthUserEntity;
import demoanalyzer.com.user.auth.domain.model.User;
import demoanalyzer.com.user.auth.domain.repository.AuthRepository;
import demoanalyzer.com.user.auth.exception.BadCredentialsException;
import demoanalyzer.com.user.auth.exception.UsernameAlreadyExistsException;
import demoanalyzer.com.securityLegacy.TokenService;
import demoanalyzer.com.user.auth.model.TokenResponseDto;
import demoanalyzer.com.user.auth.model.UserRequestDto;
import demoanalyzer.com.user.auth.model.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthRepository authRepository;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;


  public UserResponseDto register(UserRequestDto userRequest) {
    User user = convertToUser(userRequest);
    User registeredUser = registerUser(user);
    return UserResponseDto.from(registeredUser);
  }

  @Override
  public User registerUser(User user) {
    if (authRepository.findUser(user.email()).isPresent()) {
      throw new UsernameAlreadyExistsException();
    }
    String encodedPassword = passwordEncoder.encode(user.password()); // Hashowanie
    AuthUserEntity authUserEntity = new AuthUserEntity(user.email(), encodedPassword);
    AuthUserEntity savedEntity = authRepository.save(authUserEntity);
    return new User(savedEntity.getId(), savedEntity.getEmail(), savedEntity.getPassword());
  }

  public TokenResponseDto login(UserRequestDto userRequest) {
    User user = loginUser(userRequest.username(), userRequest.password());
    String token = tokenService.generateToken(user.id());
    return new TokenResponseDto(token);
  }

  @Override
  public User loginUser(String username, String password) {
    AuthUserEntity authUserEntity =
        authRepository.findUser(username).orElseThrow(BadCredentialsException::new);

    if (!passwordEncoder.matches(password, authUserEntity.getPassword())) { // Porównanie
      throw new BadCredentialsException();
    }
    return new User(authUserEntity.getId(), authUserEntity.getEmail(), authUserEntity.getPassword());
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
