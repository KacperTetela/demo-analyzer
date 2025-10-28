package demoanalyzer.com.user.auth.service;

import demoanalyzer.com.user.auth.domain.command.AuthRequestCommand;
import demoanalyzer.com.user.auth.domain.command.ChangeEmailCommand;
import demoanalyzer.com.user.auth.domain.command.ChangePasswordCommand;
import demoanalyzer.com.user.auth.domain.command.DeleteAccountCommand;
import demoanalyzer.com.user.auth.domain.model.AuthUser;
import demoanalyzer.com.user.auth.domain.model.OperationResult;
import demoanalyzer.com.user.auth.domain.repository.AuthRepository;
import demoanalyzer.com.user.auth.domain.service.AuthService;
import demoanalyzer.com.user.auth.exception.BadCredentialsException;
import demoanalyzer.com.user.auth.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthRepository repository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public OperationResult registerUser(AuthRequestCommand command) {
    if (repository.findUser(command.email()).isPresent()) {
      throw new UsernameAlreadyExistsException();
    }
    String encodedPassword = passwordEncoder.encode(command.password()); // Hashowanie
    AuthUser authUser = repository.saveUser(new AuthUser(null, command.email(), encodedPassword));
    OperationResult login = loginUser(new AuthRequestCommand(authUser.email(), command.password()));
    if (login.success()) return login;
    return new OperationResult(false, "An error occurred while creating account");
  }

  @Override
  public OperationResult loginUser(AuthRequestCommand command) {
    AuthUser authUser =
        repository
            .findUser(command.email())
            .filter(user -> passwordEncoder.matches(command.password(), user.password()))
            .orElseThrow(BadCredentialsException::new);
    return new OperationResult(true, "You are logged in");
  }

  @Override
  public OperationResult logoutUser(String accessToken) {
    return null;
  }

  @Override
  public OperationResult changeUserEmail(ChangeEmailCommand command) {
    return null;
  }

  @Override
  public OperationResult changePasswordUser(ChangePasswordCommand command) {
    return null;
  }

  @Override
  public OperationResult deleteAccountUser(DeleteAccountCommand command) {
    return null;
  }
}
