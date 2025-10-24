package demoanalyzer.com.securityLegacy.domain;

import java.util.Map;

public interface JwtService {
  String generateToken(String subject, Map<String, Object> claims);

  boolean validateToken(String token);

  String getSubject(String token);
}
