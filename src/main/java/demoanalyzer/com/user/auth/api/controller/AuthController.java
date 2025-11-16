package demoanalyzer.com.user.auth.api.controller;

import demoanalyzer.com.user.auth.api.dto.request.AuthRequest;
import demoanalyzer.com.user.auth.api.dto.request.ChangeEmailRequest;
import demoanalyzer.com.user.auth.api.dto.request.ChangePasswordRequest;
import demoanalyzer.com.user.auth.api.dto.response.JwtResponse;
import demoanalyzer.com.user.auth.domain.command.AuthCommand;
import demoanalyzer.com.user.auth.domain.command.ChangeEmailCommand;
import demoanalyzer.com.user.auth.domain.command.ChangePasswordCommand;
import demoanalyzer.com.user.auth.domain.model.OperationResult;
import demoanalyzer.com.user.auth.domain.service.AuthService;
import demoanalyzer.com.user.auth.exception.BadCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<OperationResult> registerUser(@RequestBody AuthRequest command) {
    OperationResult result =
        authService.registerUser(new AuthCommand(command.email(), command.password()));
    return ResponseEntity.ok(result);
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> loginUser(@RequestBody AuthRequest command) {
    OperationResult result =
        authService.loginUser(new AuthCommand(command.email(), command.password()));
    if (!result.success()) {
      throw BadCredentialsException;
    }
    return ResponseEntity.ok(new JwtResponse(result.message(), result.message()));
  }

  /*  @PostMapping("/refresh-token")
  public ResponseEntity<JwtResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
    JwtResponse jwtResponse = authService.refreshToken(request);
    return ResponseEntity.ok(jwtResponse);
  }*/

  @PostMapping("/logout")
  public ResponseEntity<OperationResult> logoutUser(
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    OperationResult result = authService.logoutUser(token);
    return ResponseEntity.ok(result);
  }

  @PatchMapping("/email")
  public ResponseEntity<OperationResult> changeUserEmail(@RequestBody ChangeEmailRequest command) {
    OperationResult result =
        authService.changeUserEmail(
            new ChangeEmailCommand(command.email(), command.password(), command.newEmail()));
    return ResponseEntity.ok(result);
  }

  @PatchMapping("/password")
  public ResponseEntity<OperationResult> changePasswordUser(
      @RequestBody ChangePasswordRequest command) {
    OperationResult result =
        authService.changePasswordUser(
            new ChangePasswordCommand(
                command.email(), command.oldPassword(), command.newPassword()));
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<OperationResult> deleteAccountUser(
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");
    OperationResult result = authService.deleteAccountUser(token);
    return ResponseEntity.ok(result);
  }
}
