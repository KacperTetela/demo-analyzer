package demoanalyzer.com.user.infrastructure.controller;

import demoanalyzer.com.user.domain.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    // ... metody login, register, logout

    private final AuthenticationService authenticationService;
}
