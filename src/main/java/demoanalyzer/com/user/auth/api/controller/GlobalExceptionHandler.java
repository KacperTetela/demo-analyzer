package demoanalyzer.com.user.auth.api.controller;

import demoanalyzer.com.user.auth.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("message", message);

    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
    return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<Object> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
    return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(UserSaveFailedException.class)
  public ResponseEntity<Object> handleUserSaveFailed(UserSaveFailedException ex) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler(UserUpdateFailedException.class)
  public ResponseEntity<Object> handleUserUpdateFailed(UserUpdateFailedException ex) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler(UserDeleteFailedException.class)
  public ResponseEntity<Object> handleUserDeleteFailed(UserDeleteFailedException ex) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
    return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
  }

  @ExceptionHandler(TokenGenerationException.class)
  public ResponseEntity<Object> handleTokenGeneration(TokenGenerationException ex) {
    return buildResponse(
        HttpStatus.INTERNAL_SERVER_ERROR, "Error generating authentication token.");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(Exception ex) {
      ex.printStackTrace();
    return buildResponse(
        HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
  }
}
