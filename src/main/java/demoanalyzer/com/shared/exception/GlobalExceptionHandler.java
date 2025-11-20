package demoanalyzer.com.shared.exception;

import demoanalyzer.com.user.auth.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<String> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(UserSaveFailedException.class)
  public ResponseEntity<String> handleUserSaveFailed(UserSaveFailedException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }

  @ExceptionHandler(UserUpdateFailedException.class)
  public ResponseEntity<String> handleUserUpdateFailed(UserUpdateFailedException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }

  @ExceptionHandler(UserDeleteFailedException.class)
  public ResponseEntity<String> handleUserDeleteFailed(UserDeleteFailedException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An unexpected error occurred: " + ex.getMessage());
  }
}
