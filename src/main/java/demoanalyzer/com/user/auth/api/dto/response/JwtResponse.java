package demoanalyzer.com.user.auth.api.dto.response;

public record JwtResponse(String accessToken, String refreshToken, String type) {
  public JwtResponse(String accessToken, String refreshToken) {
    this(accessToken, refreshToken, "Bearer");
  }
}
