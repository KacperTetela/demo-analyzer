package demoanalyzer.com.user.auth.domain.exception;

public class UserUpdateFailedException extends RuntimeException {
  public UserUpdateFailedException() {
    super("Failed to update user");
  }
}
