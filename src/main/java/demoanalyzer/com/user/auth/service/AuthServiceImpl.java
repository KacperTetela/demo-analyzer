package demoanalyzer.com.user.auth.service;

import demoanalyzer.com.user.auth.domain.exception.*;
import demoanalyzer.com.user.auth.domain.model.AuthTokens;
import demoanalyzer.com.user.auth.domain.model.AuthUser;
import demoanalyzer.com.user.auth.domain.repository.AuthRepository;
import demoanalyzer.com.user.auth.domain.service.AuthService;
import demoanalyzer.com.user.auth.domain.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Override
  public AuthTokens registerUser(String email, String password) {
    if (repository.findUser(email).isPresent()) {
      throw new UsernameAlreadyExistsException();
    }

    String encodedPassword = passwordEncoder.encode(password);
    AuthUser savedUser;
    try {
      savedUser = repository.saveUser(new AuthUser(null, email, encodedPassword));
      if (savedUser == null) {
        throw new UserSaveFailedException();
      }
    } catch (Exception e) {
      throw new UserSaveFailedException(e);
    }

    String accessToken = jwtService.generateAccessToken(savedUser);
    String refreshToken = jwtService.generateRefreshToken(savedUser);
    return new AuthTokens(accessToken, refreshToken);
  }

  @Override
  public AuthTokens loginUser(String email, String password) {
    AuthUser user = authenticate(email, password);
    String accessToken = jwtService.generateAccessToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    return new AuthTokens(accessToken, refreshToken);
  }

  @Override
  public void logoutUser(String accessToken) {
    jwtService.invalidateToken(accessToken);
  }

  @Override
  public AuthTokens changeUserEmail(
      String email, String password, String newEmail, String accessToken) {
    AuthUser authUser = authenticate(email, password);
    matchToken(authUser, accessToken);

    AuthUser updatedUser;
    try {
      repository.updateEmailById(authUser.id(), newEmail);
      updatedUser = repository.findUser(newEmail).orElseThrow(UserNotFoundException::new);
    } catch (Exception e) {
      throw new UserUpdateFailedException();
    }

    jwtService.invalidateToken(accessToken);
    String newAccessToken = jwtService.generateAccessToken(updatedUser);
    String refreshToken = jwtService.generateRefreshToken(updatedUser);
    return new AuthTokens(newAccessToken, refreshToken);
  }

  @Override
  public AuthTokens changePasswordUser(
      String email, String oldPassword, String newPassword, String accessToken) {
    AuthUser authUser = authenticate(email, oldPassword);
    matchToken(authUser, accessToken);
    String encodedPassword = passwordEncoder.encode(newPassword);

    AuthUser updatedUser;
    try {
      repository.updatePasswordById(authUser.id(), encodedPassword);
      updatedUser = repository.findUser(email).orElseThrow(UserNotFoundException::new);
    } catch (Exception e) {
      throw new UserUpdateFailedException();
    }

    jwtService.invalidateToken(accessToken);
    String newAccessToken = jwtService.generateAccessToken(updatedUser);
    String refreshToken = jwtService.generateRefreshToken(updatedUser);
    return new AuthTokens(newAccessToken, refreshToken);
  }

  @Override
  public void deleteAccountUser(String accessToken) {
    Long userId = jwtService.extractUserId(accessToken);

    try {
      repository.deleteUser(userId);
      jwtService.invalidateToken(accessToken);
    } catch (Exception e) {
      throw new UserDeleteFailedException();
    }
  }

  private AuthUser authenticate(String email, String password) {
    AuthUser user = repository.findUser(email).orElseThrow(UserNotFoundException::new);

    if (!passwordEncoder.matches(password, user.password())) {
      throw new BadCredentialsException();
    }

    return user;
  }

  private boolean matchToken(AuthUser user, String accessToken) {
    if (!jwtService.extractUserId(accessToken).equals(user.id()))
      throw new BadCredentialsException("Token does not match the provided user");

    return true;
  }
}
