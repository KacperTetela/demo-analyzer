package demoanalyzer.com.user.auth.domain.exception;

public class UserDeleteFailedException extends RuntimeException {
  public UserDeleteFailedException() {
    super("Failed to delete user");
  }
}
