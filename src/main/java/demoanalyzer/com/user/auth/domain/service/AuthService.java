package demoanalyzer.com.user.auth.domain.service;

import demoanalyzer.com.user.auth.domain.command.AuthCommand;
import demoanalyzer.com.user.auth.domain.command.ChangeEmailCommand;
import demoanalyzer.com.user.auth.domain.command.ChangePasswordCommand;

public interface AuthService {

  void registerUser(AuthCommand command);

  void loginUser(AuthCommand command);

  void logoutUser(String accessToken);

  void changeUserEmail(ChangeEmailCommand command);

  void changePasswordUser(ChangePasswordCommand command);

  void deleteAccountUser(String accessToken);
}
