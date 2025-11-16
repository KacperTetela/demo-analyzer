package demoanalyzer.com.user.auth.api.dto.request;

public record ChangeEmailRequest(String email, String password, String newEmail) {}