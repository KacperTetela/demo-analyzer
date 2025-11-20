package demoanalyzer.com.user.auth.domain.exception;

public class TokenGenerationException extends RuntimeException {
  public TokenGenerationException(Throwable cause) {
    super("Failed to generate user token", cause);
  }
}
