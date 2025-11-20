package demoanalyzer.com.user.auth.service;

import demoanalyzer.com.user.auth.domain.repository.AuthRepository;
import demoanalyzer.com.user.auth.domain.service.AuthService;
import demoanalyzer.com.user.auth.domain.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

class AuthServiceImplTest {

  private AuthRepository repository;
  private PasswordEncoder passwordEncoder;
  private AuthService service;
  private JwtService jwtService;

  @BeforeEach
  void setUp() {
    repository = mock(AuthRepository.class);
    passwordEncoder = mock(PasswordEncoder.class);
    jwtService = Mockito.spy(new JwtServiceImpl());
    service = Mockito.spy(new AuthServiceImpl(repository, passwordEncoder, jwtService));
  }

  @Test
  void registerUser() {}
}
