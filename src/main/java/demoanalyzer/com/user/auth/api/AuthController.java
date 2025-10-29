package demoanalyzer.com.user.auth.api;

import demoanalyzer.com.user.auth.domain.command.AuthRequestCommand;
import demoanalyzer.com.user.auth.domain.command.ChangeEmailCommand;
import demoanalyzer.com.user.auth.domain.command.ChangePasswordCommand;
import demoanalyzer.com.user.auth.domain.model.OperationResult;
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
  public ResponseEntity<OperationResult> registerUser(@RequestBody AuthRequestCommand command) {
    OperationResult result = authService.registerUser(command);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/login")
  public ResponseEntity<OperationResult> loginUser(@RequestBody AuthRequestCommand command) {
    OperationResult result = authService.loginUser(command);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/logout")
  public ResponseEntity<OperationResult> logoutUser(@RequestBody String accessToken) {
    OperationResult result = authService.logoutUser(accessToken);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/change-email")
  public ResponseEntity<OperationResult> changeUserEmail(@RequestBody ChangeEmailCommand command) {
    OperationResult result = authService.changeUserEmail(command);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/change-password")
  public ResponseEntity<OperationResult> changePasswordUser(
      @RequestBody ChangePasswordCommand command) {
    OperationResult result = authService.changePasswordUser(command);
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<OperationResult> deleteAccountUser(
      @RequestBody AuthRequestCommand command) {
    OperationResult result = authService.deleteAccountUser(command);
    return ResponseEntity.ok(result);
  }
}
