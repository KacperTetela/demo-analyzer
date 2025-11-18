package demoanalyzer.com.user.auth.api.controller;

import demoanalyzer.com.user.auth.api.dto.request.AuthRequest;
import demoanalyzer.com.user.auth.api.dto.request.ChangeEmailRequest;
import demoanalyzer.com.user.auth.api.dto.request.ChangePasswordRequest;
import demoanalyzer.com.user.auth.api.dto.response.JwtResponse;
import demoanalyzer.com.user.auth.domain.command.AuthCommand;
import demoanalyzer.com.user.auth.domain.command.ChangeEmailCommand;
import demoanalyzer.com.user.auth.domain.command.ChangePasswordCommand;
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
  public ResponseEntity<OperationResult> registerUser(@RequestBody AuthRequest request) {
    OperationResult result =
        authService.registerUser(new AuthCommand(request.email(), request.password()));
    return ResponseEntity.ok(result);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> loginUser(@RequestBody AuthRequest request) {
    OperationResult result =
        authService.loginUser(new AuthCommand(request.email(), request.password()));
    if (!result.success()) {
      throw BadCredentialsException;
    }
    return ResponseEntity.ok(new JwtResponse(result.message(), result.message()));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logoutUser(
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    OperationResult result = authService.logoutUser(token);
    return ResponseEntity.ok(result);
  }

  @PatchMapping("/email")
  public ResponseEntity<Void> changeUserEmail(@RequestBody ChangeEmailRequest request) {
    OperationResult result =
        authService.changeUserEmail(
            new ChangeEmailCommand(request.email(), request.password(), request.newEmail()));
    return ResponseEntity.ok(result);
  }

  @PatchMapping("/password")
  public ResponseEntity<Void> changePasswordUser(
      @RequestBody ChangePasswordRequest request) {
    OperationResult result =
        authService.changePasswordUser(
            new ChangePasswordCommand(
                request.email(), request.oldPassword(), request.newPassword()));
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Void> deleteAccountUser(
          @RequestHeader("Authorization") String authHeader) {

    String token = authHeader.replace("Bearer ", "");
    authService.deleteAccountUser(token);

    return ResponseEntity.noContent().build();
  }
}
