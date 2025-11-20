package demoanalyzer.com.user.auth.domain.exception;

public class UserSaveFailedException extends RuntimeException {
  public UserSaveFailedException() {
    super("Failed to save user");
  }

  public UserSaveFailedException(Throwable cause) {
    super("Failed to save user" + cause);
  }
}
