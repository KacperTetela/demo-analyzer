package demoanalyzer.com.user.auth.domain.service;

import demoanalyzer.com.user.auth.domain.model.AuthUser;

public interface JwtService {

  String generateAccessToken(AuthUser user);

  String generateRefreshToken(AuthUser user);

  boolean isTokenValid(String token);

  Long extractUserId(String accessToken);

  void invalidateToken(String token);
}
