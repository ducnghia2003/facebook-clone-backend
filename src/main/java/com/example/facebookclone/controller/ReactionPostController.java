package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.ReactionDTO;
import com.example.facebookclone.model.ResponseReaction;
import com.example.facebookclone.service.AccountService;
import com.example.facebookclone.service.ReactionPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/facebook.api/post/reactions")
@CrossOrigin
public class ReactionPostController {
    @Autowired
    private ReactionPostService reactionPostService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseReaction getReactionsByPost(@PathVariable int id) {
        return reactionPostService.getReactionsByPostId(id);
    }

    @GetMapping("/getReaction/{id}")
    public ReactionDTO getReactionToPost(@PathVariable int id, Principal principal) {
        int userId = accountService.findByUsername(principal.getName()).getId();
        return reactionPostService.getReactionToPost(userId, id);
    }

//    @GetMapping("/{id}")
//    public List<ReactionDTO> getReactionsByPost(@PathVariable int id) {
//        return reactionPostService.getReactionsByPostId(id);
//    }

    @PutMapping(value = "/updateReaction")
    public ReactionDTO updateReaction(
            @RequestParam(name = "id_account") Integer id_account,
            @RequestParam(name = "id_post") Integer id_post,
            @RequestParam(name = "type") String type

    ) {
        return reactionPostService.updateReactionPost(id_account, id_post, type);
    }
}
