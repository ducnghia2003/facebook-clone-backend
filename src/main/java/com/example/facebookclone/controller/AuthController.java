package com.example.facebookclone.controller;

import com.example.facebookclone.auth.AuthRequest;
import com.example.facebookclone.auth.AuthResponse;
import com.example.facebookclone.auth.RegisterRequest;
import com.example.facebookclone.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return  authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/checkTokenExpired")
    public boolean checkTokenExpired(@RequestParam String token) {
        return authService.checkTokenExpired(token);
    }
}
