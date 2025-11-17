package demoanalyzer.com.user.auth.domain.service;

public interface JwtService {
    Long extractUserId(String accessToken);
}
