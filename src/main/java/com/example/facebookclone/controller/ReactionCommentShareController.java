package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.ReactionCommentDTO;
import com.example.facebookclone.model.ResponseReaction;
import com.example.facebookclone.service.AccountService;
import com.example.facebookclone.service.ReactionCommentPostService;
import com.example.facebookclone.service.ReactionCommentShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facebook.api/commentshare/reactions")
@CrossOrigin
public class ReactionCommentShareController {
    @Autowired
    private ReactionCommentShareService reactionCommentShareService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseReaction getReactionsByCommentShare(@PathVariable int id) {
        return reactionCommentShareService.getReactionsByCommentId(id);
    }
    @PutMapping(value = "/updateReaction")
    public ReactionCommentDTO updateReaction(
            @RequestParam(name = "id_account") Integer id_account,
            @RequestParam(name = "id_comment") Integer id_comment,
            @RequestParam(name = "type") String type

    ) {
        return reactionCommentShareService.updateReactionCommentShare(id_account, id_comment, type);
    }
}
