package demoanalyzer.com.user.auth.api.controller;

import demoanalyzer.com.user.auth.api.dto.request.AuthRequest;
import demoanalyzer.com.user.auth.api.dto.request.ChangeEmailRequest;
import demoanalyzer.com.user.auth.api.dto.request.ChangePasswordRequest;
import demoanalyzer.com.user.auth.api.dto.response.JwtResponse;
import demoanalyzer.com.user.auth.domain.model.AuthTokens;
import demoanalyzer.com.user.auth.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<JwtResponse> registerUser(@RequestBody AuthRequest request) {
    AuthTokens newTokens = authService.registerUser(request.email(), request.password());

    return ResponseEntity.ok(JwtResponse.from(newTokens));
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> loginUser(@RequestBody AuthRequest request) {
    AuthTokens newTokens = authService.loginUser(request.email(), request.password());
    return ResponseEntity.ok(JwtResponse.from(newTokens));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logoutUser(@RequestHeader("Authorization") String oldToken) {
    authService.logoutUser(oldToken);

    return ResponseEntity.ok().build();
  }

  @PatchMapping("/email")
  public ResponseEntity<JwtResponse> changeUserEmail(
      @RequestHeader("Authorization") String oldToken, @RequestBody ChangeEmailRequest request) {

    AuthTokens newTokens =
        authService.changeUserEmail(
            request.email(), request.password(), request.newEmail(), oldToken);

    return ResponseEntity.ok(JwtResponse.from(newTokens));
  }

  @PatchMapping("/password")
  public ResponseEntity<JwtResponse> changePasswordUser(
      @RequestHeader("Authorization") String oldToken, @RequestBody ChangePasswordRequest request) {

    AuthTokens newTokens =
        authService.changePasswordUser(
            request.email(), request.oldPassword(), request.newPassword(), oldToken);

    return ResponseEntity.ok(JwtResponse.from(newTokens));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Void> deleteAccountUser(@RequestHeader("Authorization") String oldToken) {
    authService.deleteAccountUser(oldToken);

    return ResponseEntity.noContent().build();
  }
}
