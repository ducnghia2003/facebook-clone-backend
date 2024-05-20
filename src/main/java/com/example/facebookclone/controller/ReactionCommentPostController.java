package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.ReactionCommentDTO;
import com.example.facebookclone.DTO.ReactionPostDTO;
import com.example.facebookclone.model.ResponseReaction;
import com.example.facebookclone.service.AccountService;
import com.example.facebookclone.service.ReactionCommentPostService;
import com.example.facebookclone.service.ReactionPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/facebook.api/commentpost/reactions")
@CrossOrigin
public class ReactionCommentPostController {
    @Autowired
    private ReactionCommentPostService reactionCommentPostService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseReaction getReactionsByCommentPost(@PathVariable int id) {
        return reactionCommentPostService.getReactionsByCommentId(id);
    }
    @PutMapping(value = "/updateReaction")
    public ReactionCommentDTO updateReaction(
            @RequestParam(name = "id_account") Integer id_account,
            @RequestParam(name = "id_comment") Integer id_comment,
            @RequestParam(name = "type") String type

    ) {
        return reactionCommentPostService.updateReactionCommentPost(id_account, id_comment, type);
    }
}
