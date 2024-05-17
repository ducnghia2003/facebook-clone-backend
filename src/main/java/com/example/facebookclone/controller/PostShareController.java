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
}
