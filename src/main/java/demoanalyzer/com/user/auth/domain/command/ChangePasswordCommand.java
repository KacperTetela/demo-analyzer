package demoanalyzer.com.user.auth.domain.command;

public record ChangePasswordCommand(Long userId, String oldPassword, String newPassword) {}
