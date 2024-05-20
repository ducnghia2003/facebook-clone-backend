package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.CommentShareDTO;
import com.example.facebookclone.service.CommentShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/facebook.api/share/comments")
public class CommentShareController {
    @Autowired
    private CommentShareService commentShareService;

    @GetMapping("/{id}")
    List<CommentShareDTO> getCommentsByShare(@PathVariable int id) {
        return commentShareService.getCommentsByShare(id);
    }
    @PostMapping(value = "/createComment")
    public CommentShareDTO createComment(
            @RequestParam(name = "id_account") Integer id_account,
            @RequestParam(name = "id_share", required = false) Integer id_share,
            @RequestParam(name = "account_tag", required = false) Integer account_tag,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "to_comment_id", required = false) Integer to_comment_id,
            @RequestParam(name = "image", required = false) MultipartFile image
    ) {
        return commentShareService.createComment(id_account, account_tag, id_share, content, image, to_comment_id);
    }

    @PatchMapping(value = "/updateComment")
    public CommentShareDTO updateComment(
            @RequestParam(name = "id_comment") Integer id_comment,
            @RequestParam(name = "content") String content
    ) {
        return commentShareService.updateComment(id_comment, content);
    }

    @DeleteMapping(value = "deleteComment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id) {
        commentShareService.deleteComment(id);
        return ResponseEntity.ok("Delete comment successfully");
    }
}
