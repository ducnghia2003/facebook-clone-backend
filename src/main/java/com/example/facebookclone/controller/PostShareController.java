package com.example.facebookclone.controller;

import com.example.facebookclone.service.PostShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/facebook.api/postshare")
public class PostShareController {
    @Autowired
    private PostShareService postShareService;
    @GetMapping("/personal/{id}")
    List<Object> getListPostAndShareOfPersonal(@PathVariable int id) {
        return postShareService.getListPostAndShareOfPersonal(id);
    }

    @GetMapping("/friends/{id}")
    List<Object> getListPostAndShareOfFriends(@PathVariable int id) {
        return postShareService.getListPostAndShareOfFriends(id);
    }

    @GetMapping("/other")
    List<Object> getListPostAndShareOfOther(
            @RequestParam(name = "id_account") int id_account,
            @RequestParam(name = "id_other") int id_other
    ) {
        return postShareService.getListPostAndShareOfOther(id_account, id_other);
    }
}
