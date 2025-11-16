package demoanalyzer.com.user.auth.api.dto.request;

public record ChangePasswordRequest(String email, String oldPassword, String newPassword) {}
