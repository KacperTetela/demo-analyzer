package demoanalyzer.com.user.auth.service.legacy;

import demoanalyzer.com.user.auth.persistence.AuthUserEntity;
import demoanalyzer.com.user.auth.domain.model.AuthUser;
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

  @Override
  public AuthUser loginUser(String username, String password) {
    AuthUserEntity authUserEntity =
        authRepository.findUser(username).orElseThrow(BadCredentialsException::new);

    if (!passwordEncoder.matches(password, authUserEntity.getPassword())) { // Porównanie
      throw new BadCredentialsException();
    }
    return new AuthUser(
        authUserEntity.getId(), authUserEntity.getEmail(), authUserEntity.getPassword());
  }
}
