package demoanalyzer.com.dem.domain.exception;

public class DemNotFoundException extends RuntimeException {
  public DemNotFoundException(Long id) {
    super("Demo with id " + id + " not found");
  }
}
