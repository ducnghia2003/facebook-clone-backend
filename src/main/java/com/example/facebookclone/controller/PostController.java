package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.PostDTO;
import com.example.facebookclone.entity.Post;
import com.example.facebookclone.service.AccountService;
import com.example.facebookclone.service.FriendService;
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
    @Autowired
    private FriendService friendService;

    @GetMapping("/{id}")
    public List<PostDTO> getPostsByAccountId(@PathVariable int id, Principal principal) {
        int account_id = accountService.findByUsername(principal.getName()).getId();
        if (id == account_id)
            return postService.getPersonalPost(account_id).stream().sorted(Comparator.comparing(PostDTO::getCreate_time).reversed()).toList();
        else if (friendService.isFriend(account_id, id))
            return postService.getFriendPost(id, account_id).stream().sorted(Comparator.comparing(PostDTO::getCreate_time).reversed()).toList();
        else
            return postService.getStrangerPost(id, account_id).stream().sorted(Comparator.comparing(PostDTO::getCreate_time).reversed()).toList();
    }

    @GetMapping("/getPersonalPost")
    public List<PostDTO> getPersonalPost(Principal principal) {
        int id = accountService.findByUsername(principal.getName()).getId();
        return postService.getPersonalPost(id).stream().sorted(Comparator.comparing(PostDTO::getCreate_time).reversed()).toList();
    }
//    @GetMapping
//    public List<PostDTO> getPostsByAccountId(Principal principal) {
//        int id = accountService.findByUsername(principal.getName()).getId();
//        return postService.getPostsByAccountId(id).stream().sorted(Comparator.comparing(PostDTO::getCreate_time).reversed()).toList();
//    }
//    @GetMapping("/friends/{id}")
//    List<PostDTO> getListPostOfFriends(@PathVariable int id) {
//        return postService.getListPostOfFriends(id).stream().sorted(Comparator.comparing(PostDTO::getCreate_time).reversed()).toList();
//    }

//    @GetMapping("/other")
//    List<PostDTO> getListPostOfOther(
//            @RequestParam(name = "id_account") int id_account,
//            @RequestParam(name = "id_other") int id_other
//    ) {
//        return postService.getListPostOfOther(id_account, id_other).stream().sorted(Comparator.comparing(PostDTO::getCreate_time).reversed()).toList();
//    }

    @PostMapping(value = "/createPost")
    public PostDTO createPost(Principal principal,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "view_mode") String view_mode,
            @RequestParam(name = "images", required = false) List<MultipartFile> images,
            @RequestParam(name = "shareId", required = false) Integer shareId
    ) {
        int id_account = accountService.findByUsername(principal.getName()).getId();
        return postService.savePost(id_account, content, view_mode, images, shareId);
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
    public PostDTO getPostById(@PathVariable int id, Principal principal) {
        return postService.getPostById(id);
    }

    @GetMapping("/getAllFriendPost")
    public List<PostDTO> getAllFriendPost(Principal principal) {
        int id = accountService.findByUsername(principal.getName()).getId();
        return postService.getAllFriendPost(id);
    }
}
