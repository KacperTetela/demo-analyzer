package demoanalyzer.com.user.auth.api.controller;

import demoanalyzer.com.user.auth.api.dto.request.AuthRequest;
import demoanalyzer.com.user.auth.api.dto.request.ChangeEmailRequest;
import demoanalyzer.com.user.auth.api.dto.request.ChangePasswordRequest;
import demoanalyzer.com.user.auth.api.dto.response.JwtResponse;
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
    String token = authService.registerUser(request.email(), request.password());

    return ResponseEntity.ok(new JwtResponse(token, null));
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> loginUser(@RequestBody AuthRequest request) {
    String token = authService.loginUser(request.email(), request.password());
    return ResponseEntity.ok(new JwtResponse(token, null));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logoutUser(@RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    authService.logoutUser(token);

    return ResponseEntity.ok().build();
  }

  @PatchMapping("/email")
  public ResponseEntity<JwtResponse> changeUserEmail(
      @RequestHeader("Authorization") String authHeader, @RequestBody ChangeEmailRequest request) {

    String token = authHeader.replace("Bearer ", "");
    String newToken =
        authService.changeUserEmail(
            request.email(), request.password(), request.newEmail(), token);

    return ResponseEntity.ok(new JwtResponse(newToken, null));
  }

  @PatchMapping("/password")
  public ResponseEntity<JwtResponse> changePasswordUser(
      @RequestHeader("Authorization") String authHeader,
      @RequestBody ChangePasswordRequest request) {

    String token = authHeader.replace("Bearer ", "");
    String newToken =
        authService.changePasswordUser(
            request.email(), request.oldPassword(), request.newPassword(), token);

    return ResponseEntity.ok(new JwtResponse(newToken, null));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Void> deleteAccountUser(@RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    authService.deleteAccountUser(token);

    return ResponseEntity.noContent().build();
  }
}
