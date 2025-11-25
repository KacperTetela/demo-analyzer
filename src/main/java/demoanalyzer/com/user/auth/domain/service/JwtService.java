package demoanalyzer.com.user.auth.domain.service;

import demoanalyzer.com.user.auth.domain.model.AuthUser;

public interface JwtService {

  String generateAccessToken(AuthUser user);

  String generateRefreshToken(AuthUser user);

  Long extractUserId(String accessToken);

  void invalidateToken(String token);

  boolean isAccessTokenValid(String token);

  boolean isRefreshTokenValid(String token);
}
