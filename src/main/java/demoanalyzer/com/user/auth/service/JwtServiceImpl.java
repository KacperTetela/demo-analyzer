package demoanalyzer.com.user.auth.service;

import demoanalyzer.com.user.auth.domain.exception.TokenGenerationException;
import demoanalyzer.com.user.auth.domain.model.AuthUser;
import demoanalyzer.com.user.auth.domain.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.access.expiration.ms}")
  private long accessExpirationMs;

  @Value("${jwt.refresh.expiration.ms}")
  private long refreshExpirationMs;

  private final Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();

  @Override
  public String generateAccessToken(AuthUser user) {
    try {
      Date now = new Date();
      Date expiryDate = new Date(now.getTime() + accessExpirationMs);

      return Jwts.builder()
          .setSubject(String.valueOf(user.id()))
          .claim("email", user.email())
          .claim("type", "ACCESS")
          .setIssuedAt(now)
          .setExpiration(expiryDate)
          .signWith(SignatureAlgorithm.HS256, secretKey)
          .compact();
    } catch (Exception e) {
      throw new TokenGenerationException(e);
    }
  }

  @Override
  public String generateRefreshToken(AuthUser user) {
    try {
      Date now = new Date();
      Date expiryDate = new Date(now.getTime() + refreshExpirationMs);

      return Jwts.builder()
          .setSubject(String.valueOf(user.id()))
          .claim("type", "REFRESH")
          .setIssuedAt(now)
          .setExpiration(expiryDate)
          .signWith(SignatureAlgorithm.HS256, secretKey)
          .compact();
    } catch (Exception e) {
      throw new TokenGenerationException(e);
    }
  }

  @Override
  public Long extractUserId(String accessToken) {
    Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
    return Long.parseLong(claims.getSubject());
  }

  @Override
  public void invalidateToken(String token) {
    invalidatedTokens.add(token);
  }

  @Override
  public boolean isAccessTokenValid(String token) {
    return isTokenValid(token) && "ACCESS".equals(getTokenType(token));
  }

  @Override
  public boolean isRefreshTokenValid(String token) {
    return isTokenValid(token) && "REFRESH".equals(getTokenType(token));
  }

  private String getTokenType(String token) {
    Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

    return (String) claims.get("type");
  }

  private boolean isTokenValid(String token) {
    if (invalidatedTokens.contains(token)) {
      return false;
    }

    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
