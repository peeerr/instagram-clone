package com.peeerr.instagram.controller;

import com.peeerr.instagram.domain.user.User;
import com.peeerr.instagram.dto.auth.SignupRequest;
import com.peeerr.instagram.exception.ex.CustomValidationException;
import com.peeerr.instagram.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupRequest signupRequest,
                         BindingResult bindingResult) {
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        User user = signupRequest.toEntity();

        authService.signup(user);

        return "auth/signin";
    }

}
