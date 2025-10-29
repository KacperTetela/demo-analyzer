package demoanalyzer.com.user.auth.domain.command;

public record ChangeEmailCommand(String email, String password, String newEmail) {}
