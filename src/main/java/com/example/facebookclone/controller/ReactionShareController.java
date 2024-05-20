package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.ReactionPostDTO;
import com.example.facebookclone.DTO.ReactionShareDTO;
import com.example.facebookclone.model.ResponseReaction;
import com.example.facebookclone.service.AccountService;
import com.example.facebookclone.service.ReactionShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/facebook.api/share/reactions")
@CrossOrigin
public class ReactionShareController {
    @Autowired
    private ReactionShareService reactionShareService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseReaction getReactionsByShare(@PathVariable int id) {
        return reactionShareService.getReactionsByShareId(id);
    }

    @GetMapping("/getReaction/{id}")
    public ReactionShareDTO getReactionToShare(@PathVariable int id, Principal principal) {
        int userId = accountService.findByUsername(principal.getName()).getId();
        return reactionShareService.getReactionToShare(userId, id);
    }

//    @GetMapping("/{id}")
//    public List<ReactionDTO> getReactionsByPost(@PathVariable int id) {
//        return reactionPostService.getReactionsByPostId(id);
//    }

    @PutMapping(value = "/updateReaction")
    public ReactionShareDTO  updateReaction(
            @RequestParam(name = "id_account") Integer id_account,
            @RequestParam(name = "id_share") Integer id_share,
            @RequestParam(name = "type") String type

    ) {
        return reactionShareService.updateReactionShare(id_account, id_share, type);
    }
}
