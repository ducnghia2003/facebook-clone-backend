package com.example.facebookclone.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }
}
