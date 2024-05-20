package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.UserProfileDTO;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/account")
public class AccountController {
    final private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/getUserAccount")
    public UserProfileDTO getAccount(Principal principal) {
        return accountService.convertToUserProfileDTO(accountService.findByUsername(principal.getName()));
    }

    @GetMapping("/getUserAccount/{id}")
    public UserProfileDTO getAccountById(@PathVariable int id) {
        return accountService.convertToUserProfileDTO(accountService.findByAccountId(id));
    }
}
