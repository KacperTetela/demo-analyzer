package demoanalyzer.com.user.model;

public record ChangePasswordRequestDto(String username, String oldPassword, String newPassword) {}
