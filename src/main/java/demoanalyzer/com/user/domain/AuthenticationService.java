package demoanalyzer.com.user.domain;

public interface AuthenticationService {
  User registerUser(User user);

  User loginUser(String username, String password);

  void logoutUser(String accessToken);
}
