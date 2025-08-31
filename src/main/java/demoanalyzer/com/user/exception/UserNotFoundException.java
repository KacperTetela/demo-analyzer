package demoanalyzer.com.user.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException() {
    super("User has not been found");
  }
}
