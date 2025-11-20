package demoanalyzer.com.user.auth.domain.service;

public interface AuthService {

  String registerUser(String email, String password);

  String loginUser(String email, String password);

  void logoutUser(String accessToken);

  String changeUserEmail(String email, String password, String newEmail, String accessToken);

  String changePasswordUser(String email, String oldPassword, String newPassword, String accessToken);

  void deleteAccountUser(String accessToken);
}
