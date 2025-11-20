package demoanalyzer.com.user.auth.service;

import demoanalyzer.com.user.auth.domain.model.AuthUser;
import demoanalyzer.com.user.auth.domain.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final String secretKey = "q8y3fX0J2+9Z1V4bM6k3v7u1sH9yL2eM5rJx0WzB2cQ=!";
  private final long expirationMs = 24 * 60 * 60 * 1000;
  private final Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();

  @Override
  public String generateToken(AuthUser user) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expirationMs);

    return Jwts.builder()
        .setSubject(String.valueOf(user.id()))
        .claim("email", user.email())
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  @Override
  public boolean isTokenValid(String token) {
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

  @Override
  public Long extractUserId(String accessToken) {
    Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
    return Long.parseLong(claims.getSubject());
  }

  @Override
  public void invalidateToken(String token) {
    invalidatedTokens.add(token);
  }
}
