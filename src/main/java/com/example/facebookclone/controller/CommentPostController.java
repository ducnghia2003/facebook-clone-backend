package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.CommentPostDTO;
import com.example.facebookclone.DTO.PostDTO;
import com.example.facebookclone.service.AccountService;
import com.example.facebookclone.service.CommentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/facebook.api/post/comments")
@CrossOrigin
public class CommentPostController {
    @Autowired
    private CommentPostService commentPostService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    List<CommentPostDTO> getCommentsByPost(@PathVariable int id, Principal principal) {
        int userId = accountService.findByUsername(principal.getName()).getId();
        return commentPostService.getCommentsByPost(id, userId);
    }
    @PostMapping(value = "/createComment")
    public CommentPostDTO createComment(
            @RequestParam(name = "id_account") Integer id_account,
            @RequestParam(name = "id_post", required = false) Integer id_post,
            @RequestParam(name = "account_tag", required = false) Integer account_tag,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "to_comment_id", required = false) Integer to_comment_id,
            @RequestParam(name = "image", required = false)MultipartFile image
            ) {
        return commentPostService.createComment(id_account, account_tag, id_post, content, image, to_comment_id);
    }

    @PatchMapping(value = "/updateComment")
    public CommentPostDTO updateComment(
            @RequestParam(name = "id_comment") Integer id_comment,
            @RequestParam(name = "content") String content
    ) {
        return commentPostService.updateComment(id_comment, content);
    }

    @DeleteMapping(value = "deleteComment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id) {
        commentPostService.deleteComment(id);
        return ResponseEntity.ok("Delete comment successfully");
    }

    @GetMapping(value = "getPostOfComment/{id}")
    public PostDTO getPostOfComment(@PathVariable int id) {
        return commentPostService.getPostOfComment(id);
    }
}
