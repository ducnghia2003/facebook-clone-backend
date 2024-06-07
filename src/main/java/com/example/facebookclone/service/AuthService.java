package com.example.facebookclone.service;

import com.example.facebookclone.auth.AuthRequest;
import com.example.facebookclone.auth.AuthResponse;
import com.example.facebookclone.auth.RegisterRequest;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.repository.AccountRepository;
import com.example.facebookclone.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final PasswordEncoder PasswordEncoder;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, AccountRepository accountRepository, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        PasswordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(AuthRequest req) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtUtil.generateToken(req.getUsername());
        return new AuthResponse(token);
    }

    public void register(RegisterRequest req) {
        if (accountRepository.findByUsername(req.getUsername()) != null) {
            throw new IllegalStateException("Username already taken");
        }

        Account account = new Account();
        account.setUsername(req.getUsername());
        account.setPassword(PasswordEncoder.encode(req.getPassword()));
        account.setEmail(req.getEmail());
        account.setBrithdate(req.getDate_of_birth());
        account.setSex(req.isGender());
        account.setProfile_name(req.getFull_name());
        account.setCreate_time(LocalDateTime.now());

        accountRepository.save(account);
    }

    public boolean checkTokenExpired(String token) {
        return jwtUtil.isTokenExpired(token);
    }
}
