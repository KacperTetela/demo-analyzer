package demoanalyzer.com.user.auth.domain.command;

public record ChangePasswordCommand(String email, String oldPassword, String newPassword) {}
