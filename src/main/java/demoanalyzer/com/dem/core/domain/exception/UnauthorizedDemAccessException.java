package demoanalyzer.com.dem.core.domain.exception;

public class UnauthorizedDemAccessException extends RuntimeException {
  public UnauthorizedDemAccessException() {
    super("You are not the owner of this demo");
  }
}
