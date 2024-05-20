package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.PostDTO;
import com.example.facebookclone.entity.Post;
import com.example.facebookclone.service.AccountService;
import com.example.facebookclone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/facebook.api/posts")
@CrossOrigin
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;
    @GetMapping("/{id}")
    public List<PostDTO> getPostsByAccountId(@PathVariable int id) {
        return postService.getPostsByAccountId(id).stream().sorted(Comparator.comparing(PostDTO::getCreate_time).reversed()).toList();
    }

//    @GetMapping
//    public List<PostDTO> getPostsByAccountId(Principal principal) {
//        int id = accountService.findByUsername(principal.getName()).getId();
//        return postService.getPostsByAccountId(id).stream().sorted(Comparator.comparing(PostDTO::getCreate_time).reversed()).toList();
//    }

//    @GetMapping("/{id}")
//    public PostDTO getPostById(@PathVariable int id) {
//        return postService.getPostById(id);
//    }

//    @PostMapping(value = "/createPost")
//    public PostDTO createPost(
//            @RequestParam(name = "id_account") Integer id_account,
//            @RequestParam(name = "content", required = false) String content,
//            @RequestParam(name = "view_mode") String view_mode,
//            @RequestParam(name = "images", required = false) List<MultipartFile> images
//    ) {
//        return postService.savePost(id_account, content, view_mode, images);
//    }

    @PostMapping(value = "/createPost")
    public PostDTO createPost(Principal principal,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "view_mode") String view_mode,
            @RequestParam(name = "images", required = false) List<MultipartFile> images
    ) {
        int id_account = accountService.findByUsername(principal.getName()).getId();
        return postService.savePost(id_account, content, view_mode, images);
    }

    @PatchMapping(value = "/updatePost")
    public PostDTO updatePost(
            @RequestParam(name = "id_post") Integer id_post,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "view_mode", required = false) String view_mode,
            @RequestParam(name = "images", required = false) List<MultipartFile> images
    ){
        return postService.updatePost(id_post, content, view_mode, images);
    }


    @DeleteMapping(value = "/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Delete Post successfully");
    }

    @GetMapping("/getPostById/{id}")
    public PostDTO getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }
}
