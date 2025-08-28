package demoanalyzer.com.user.controller;

import demoanalyzer.com.domain.user.AuthenticationService;
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
