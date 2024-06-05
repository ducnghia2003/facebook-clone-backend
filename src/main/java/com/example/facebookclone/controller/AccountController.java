package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.UserDetailDTO;
import com.example.facebookclone.DTO.UserProfileDTO;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.service.AccountService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

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

    @PutMapping("/updateDetailInfo")
    public UserProfileDTO updateDetailInfo(@RequestBody UserDetailDTO userProfileDTO, Principal principal) {
        Account account = accountService.findByUsername(principal.getName());

        account.setLive_at(userProfileDTO.getAddress());
        account.setBrithdate(userProfileDTO.getBirthdate());
        account.setCome_from(userProfileDTO.getFrom());

        accountService.updateDetailInfo(account);
        return accountService.convertToUserProfileDTO(account);
    }
    @PutMapping("/updateAvatar")
    public void updateAvatar(@RequestParam MultipartFile avatar, Principal principal) throws IOException {
        int id = accountService.findByUsername(principal.getName()).getId();
        accountService.updateAvatar(avatar, id);
    }
    @PutMapping("/updateDescription")
    public UserProfileDTO updateDescription(@RequestBody Map<String, String> body, Principal principal) {
        Account account = accountService.findByUsername(principal.getName());
        account.setDescription(body.get("description"));

        accountService.updateDetailInfo(account);
        return accountService.convertToUserProfileDTO(account);
    }
    @PutMapping("/updateCoverImage")
    public void updateCoverImage(@RequestParam MultipartFile coverImage, Principal principal) throws IOException {
        int id = accountService.findByUsername(principal.getName()).getId();
        accountService.updateCoverImage(coverImage, id);
    }
}
