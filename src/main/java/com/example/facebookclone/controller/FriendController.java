package com.example.facebookclone.controller;

import com.example.facebookclone.DTO.UserProfileDTO;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Friend;
import com.example.facebookclone.service.AccountService;
import com.example.facebookclone.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {
    private final FriendService friendService;
    private final AccountService accountService;

    public FriendController(FriendService friendService, AccountService accountService) {
        this.friendService = friendService;
        this.accountService = accountService;
    }

    @PostMapping("/addFriend/{receiverId}")
    public ResponseEntity<?> addFriend(@PathVariable Integer receiverId, Principal principal) {
        try {
            int senderId = accountService.findByUsername(principal.getName()).getId();
            friendService.addFriend(senderId, receiverId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/acceptFriend/{senderId}")
    public ResponseEntity<?> acceptFriend(@PathVariable int senderId, Principal principal) {
        try {
            int receiverId = accountService.findByUsername(principal.getName()).getId();
            friendService.acceptFriend(senderId, receiverId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/rejectFriend/{senderId}")
    public ResponseEntity<?> rejectFriend(@PathVariable int senderId, Principal principal) {
        try {
            int receiverId = accountService.findByUsername(principal.getName()).getId();
            friendService.rejectFriend(senderId, receiverId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/cancelRequest/{receiverId}")
    public ResponseEntity<?> cancelRequest(@PathVariable int receiverId, Principal principal) {
        try {
            int senderId = accountService.findByUsername(principal.getName()).getId();
            friendService.rejectFriend(senderId, receiverId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllRequest")
    public ResponseEntity<?> getAllRequest(Principal principal) {
        try {
            int receiverId = accountService.findByUsername(principal.getName()).getId();
            return ResponseEntity.ok(friendService.getAllRequestUser(receiverId));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/friendList/{id}")
    public ResponseEntity<?> friendList(@PathVariable int id) {
        try {
            Account account = accountService.findByAccountId(id);

            List<UserProfileDTO> listFriend = new ArrayList<>();

            for (Friend friend : account.getFriends()) {
                if (friend.getAccept_time() != null) {
                    Account friend_account = accountService.findByAccountId(friend.getFriendId().getReceiverId());
                    listFriend.add(accountService.convertToUserProfileDTO(friend_account));
                }
            }
            for (Friend friend : account.getFriendOf()) {
                if (friend.getAccept_time() != null) {
                    Account friend_account = accountService.findByAccountId(friend.getFriendId().getSenderId());
                    listFriend.add(accountService.convertToUserProfileDTO(friend_account));
                }
            }

            return ResponseEntity.ok(listFriend);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/removeFriend/{friendId}")
    public ResponseEntity<?> removeFriend(@PathVariable int friendId, Principal principal) {
        try {
            int userId = accountService.findByUsername(principal.getName()).getId();
            friendService.removeFriend(userId, friendId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/searchUser")
    public ResponseEntity<?> searchUser(@RequestParam String name, Principal principal) {
        try {
            int userId = accountService.findByUsername(principal.getName()).getId();
            return ResponseEntity.ok(friendService.searchUser(name, userId));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profileStatus/{id}")
    public ResponseEntity<?> getStatus(@PathVariable int id, Principal principal) {
        try {
            int userId = accountService.findByUsername(principal.getName()).getId();
            return ResponseEntity.ok(friendService.getProfileStatus(userId, id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
