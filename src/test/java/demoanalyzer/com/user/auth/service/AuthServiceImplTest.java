package demoanalyzer.com.user.auth.service;

import demoanalyzer.com.user.auth.domain.model.AuthTokens;
import demoanalyzer.com.user.auth.domain.model.AuthUser;
import demoanalyzer.com.user.auth.domain.repository.AuthRepository;
import demoanalyzer.com.user.auth.domain.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

  // 1. TWORZYMY ATRAPY (MOCKI) ZALEŻNOŚCI
  @Mock AuthRepository repository;
  @Mock PasswordEncoder passwordEncoder;
  @Mock JwtService jwtService;

  // 2. WSTRZYKUJEMY ATRAPY DO TESTOWANEJ KLASY
  @InjectMocks AuthServiceImpl authService;

  @Test
  void czyRejestracjaZwracaPoprawneTokeny() {

    // --- ETAP 1: PRZYGOTOWANIE (Ustawiamy, co mają robić atrapy) ---

    // Mówimy bazie: "gdy ktoś szuka tego maila, powiedz, że go nie ma" (czyli mail jest wolny)
    when(repository.findUser("jan@test.pl")).thenReturn(Optional.empty());

    // Mówimy koderowi haseł: "jak dostaniesz 'haslo123', zamień je na 'zakodowane123'"
    when(passwordEncoder.encode("haslo123")).thenReturn("zakodowane123");

    // Tworzymy sztucznego użytkownika, którego "niby" zapisała baza
    AuthUser sztucznyUser = new AuthUser(1L, "jan@test.pl", "zakodowane123");
    when(repository.saveUser(any())).thenReturn(sztucznyUser);
    Collection collection = new ArrayList();
    collection.add(new String("ss"));


    // Mówimy serwisowi od tokenów: "wygeneruj takie tokeny dla naszego sztucznego usera"
    when(jwtService.generateAccessToken(sztucznyUser)).thenReturn("super-token");
    when(jwtService.generateRefreshToken(sztucznyUser)).thenReturn("super-refresh-token");

    // --- ETAP 2: AKCJA (Uruchamiamy prawdziwy kod) ---

    AuthTokens wynik = authService.registerUser("jan@test.pl", "haslo123");

    // --- ETAP 3: SPRAWDZENIE (Czy wynik jest zgodny z planem?) ---

    // Sprawdzamy, czy token, który zwróciła metoda, to nasz "super-token"
    assertEquals("super-token", wynik.accessToken());
  }
}
