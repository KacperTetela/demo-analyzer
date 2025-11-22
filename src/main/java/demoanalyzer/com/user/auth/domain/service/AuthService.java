package demoanalyzer.com.user.auth.domain.service;

import demoanalyzer.com.user.auth.domain.model.AuthTokens;

public interface AuthService {

  AuthTokens registerUser(String email, String password);

  AuthTokens loginUser(String email, String password);

  void logoutUser(String accessToken);

  AuthTokens changeUserEmail(String email, String password, String newEmail, String accessToken);

  AuthTokens changePasswordUser(String email, String oldPassword, String newPassword, String accessToken);

  void deleteAccountUser(String accessToken);
}
