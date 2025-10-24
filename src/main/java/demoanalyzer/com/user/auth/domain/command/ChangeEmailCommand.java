package demoanalyzer.com.user.auth.domain.command;

public record ChangeEmailCommand(Long userId, String oldEmail, String newEmail) {}
