package com.smarterfit.modules.useraccess.controller;

import com.smarterfit.modules.useraccess.dto.request.user.LoginRequestDTO;
import com.smarterfit.modules.useraccess.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.login(requestDTO));
    }
}
