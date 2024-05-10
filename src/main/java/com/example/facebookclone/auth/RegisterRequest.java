package com.example.facebookclone.auth;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private LocalDate date_of_birth;
    private boolean gender;
    private String full_name;
}
