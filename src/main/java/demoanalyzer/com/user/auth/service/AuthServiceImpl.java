package demoanalyzer.com.user.auth.service;

import demoanalyzer.com.user.auth.domain.command.AuthCommand;
import demoanalyzer.com.user.auth.domain.command.ChangeEmailCommand;
import demoanalyzer.com.user.auth.domain.command.ChangePasswordCommand;
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
  public OperationResult registerUser(AuthCommand command) {
    if (repository.findUser(command.email()).isPresent()) {
      throw new UsernameAlreadyExistsException();
    }
    String encodedPassword = passwordEncoder.encode(command.password()); // Hashowanie
    AuthUser authUser = repository.saveUser(new AuthUser(null, command.email(), encodedPassword));
    OperationResult login = loginUser(new AuthCommand(authUser.email(), command.password()));
    if (login.success()) return login;
    return new OperationResult(false, "An error occurred while creating account");
  }

  @Override
  public OperationResult loginUser(AuthCommand command) {
    AuthUser authUser = authenticate(command.email(), command.password());
    return new OperationResult(true, "You are logged in");
  }

  @Override
  public OperationResult logoutUser(String accessToken) {
    return new OperationResult(false, "No implementation of module");
  }

  @Override
  public OperationResult changeUserEmail(ChangeEmailCommand command) {
    AuthUser authUser = authenticate(command.email(), command.password());
    repository.updateEmailById(authUser.id(), command.newEmail());
    return new OperationResult(true, "Email has been updated");
  }

  @Override
  public OperationResult changePasswordUser(ChangePasswordCommand command) {
    AuthUser authUser = authenticate(command.email(), command.oldPassword());
    String encodedPassword = passwordEncoder.encode(command.newPassword());
    repository.updatePasswordById(authUser.id(), encodedPassword);
    return new OperationResult(true, "Password has been updated");
  }

  @Override
  public OperationResult deleteAccountUser(AuthCommand command) {
    AuthUser authUser = authenticate(command.email(), command.password());
    repository.deleteUser(authUser.id());
    return new OperationResult(true, "Account has been deleted");
  }

  private AuthUser authenticate(String email, String password) {
    return repository
        .findUser(email)
        .filter(user -> passwordEncoder.matches(password, user.password()))
        .orElseThrow(BadCredentialsException::new);
  }
}
