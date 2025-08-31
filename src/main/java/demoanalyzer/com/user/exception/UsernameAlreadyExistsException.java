package demoanalyzer.com.user.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
  public UsernameAlreadyExistsException(String message) {
    super(message);
  }

  public UsernameAlreadyExistsException() {
    super("Username already exists");
  }
}
