package demoanalyzer.com.user.auth.api.dto.response;

import demoanalyzer.com.user.auth.domain.model.AuthTokens;

public record JwtResponse(String accessToken, String refreshToken) {
  public static JwtResponse from(AuthTokens tokens) {
    return new JwtResponse(tokens.accessToken(), tokens.refreshToken());
  }
}
