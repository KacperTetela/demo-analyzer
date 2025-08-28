package demoanalyzer.com.exception;

public class BadCredentialsException extends RuntimeException {
  public BadCredentialsException(String message) {
    super(message);
  }

  public BadCredentialsException() {
    super("Provided Credentials are wrong");
  }
}
