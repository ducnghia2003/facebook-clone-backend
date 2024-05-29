package com.example.facebookclone.controller;

import com.example.facebookclone.entity.Account;
import com.example.facebookclone.service.AccountService;
import com.example.facebookclone.service.PostShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/facebook.api/postshare")
public class PostShareController {
    @Autowired
    private PostShareService postShareService;
    @Autowired
    private AccountService accountService;
    @GetMapping("/personal")
    List<Object> getListPostAndShareOfPersonal(Principal principal) {
        Account account = accountService.findByUsername(principal.getName());
        return postShareService.getListPostAndShareOfPersonal(account.getId());
    }

    @GetMapping("/friends")
    List<Object> getListPostAndShareOfFriends(Principal principal) {
        Account account = accountService.findByUsername(principal.getName());
        return postShareService.getListPostAndShareOfFriends(account.getId());
    }

    @GetMapping("/other")
    List<Object> getListPostAndShareOfOther(
            @RequestParam(name = "id_account") int id_account,
            @RequestParam(name = "id_other") int id_other
    ) {
        return postShareService.getListPostAndShareOfOther(id_account, id_other);
    }
}
