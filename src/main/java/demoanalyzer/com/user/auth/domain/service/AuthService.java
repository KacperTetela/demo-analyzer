package demoanalyzer.com.user.auth.domain.service;

import demoanalyzer.com.user.auth.domain.command.AuthRequestCommand;
import demoanalyzer.com.user.auth.domain.command.ChangeEmailCommand;
import demoanalyzer.com.user.auth.domain.command.ChangePasswordCommand;
import demoanalyzer.com.user.auth.domain.model.OperationResult;

public interface AuthService {

  OperationResult registerUser(AuthRequestCommand command);

  OperationResult loginUser(AuthRequestCommand command);

  OperationResult logoutUser(String accessToken);

  OperationResult changeUserEmail(ChangeEmailCommand command);

  OperationResult changePasswordUser(ChangePasswordCommand command);

  OperationResult deleteAccountUser(AuthRequestCommand command);
}
