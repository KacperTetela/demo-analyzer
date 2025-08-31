package demoanalyzer.com.user.model;

import demoanalyzer.com.user.domain.User;

public record UserResponseDto(Long id, String username) {

  public static UserResponseDto from(User user) {
    return new UserResponseDto(user.id(), user.username());
  }
}
