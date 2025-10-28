package demoanalyzer.com.user.auth.service;

import demoanalyzer.com.user.auth.domain.command.AuthRequestCommand;
import demoanalyzer.com.user.auth.domain.model.AuthUser;
import demoanalyzer.com.user.auth.domain.model.OperationResult;
import demoanalyzer.com.user.auth.domain.repository.AuthRepository;
import demoanalyzer.com.user.auth.domain.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

  private AuthRepository repository;
  private PasswordEncoder passwordEncoder;
  private AuthService service;

  @BeforeEach
  void setUp() {
    repository = mock(AuthRepository.class);
    passwordEncoder = mock(PasswordEncoder.class);
    service = Mockito.spy(new AuthServiceImpl(repository, passwordEncoder));
  }

  @Test
  void registerUser() {
    // given
    AuthRequestCommand command = new AuthRequestCommand("test@example.com", "password123");
    AuthUser savedUser = new AuthUser(1L, command.email(), "encodedPassword");

    when(repository.findUser(command.email())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(command.password())).thenReturn("encodedPassword");
    when(repository.saveUser(any(AuthUser.class))).thenReturn(savedUser);

    // loginUser będzie wywołane z prawidłowymi danymi, więc symulujemy sukces
    doReturn(new OperationResult(true, "OK"))
        .when(service)
        .loginUser(any(AuthRequestCommand.class));

    // when
    OperationResult result = service.registerUser(command);

    // then
    assertTrue(result.success());
    assertEquals("OK", result.message());
    verify(repository).findUser(command.email());
    verify(repository).saveUser(any(AuthUser.class));
    verify(service).loginUser(new AuthRequestCommand(command.email(), command.password()));
  }
}
