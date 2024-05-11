package com.example.facebookclone.service;

import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Friend;
import com.example.facebookclone.entity.embeddedID.FriendId;
import com.example.facebookclone.repository.AccountRepository;
import com.example.facebookclone.repository.FriendRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final AccountService accountService;

    public FriendService(FriendRepository friendRepository, AccountService accountService) {
        this.friendRepository = friendRepository;
        this.accountService = accountService;
    }

    public void addFriend(int senderId, int receiverId) {
        Account sender = accountService.findByAccountId(senderId);
        Account receiver = accountService.findByAccountId(receiverId);

        Friend friend = new Friend();
        friend.setFriendId(new FriendId(senderId, receiverId));
        friend.setSender(sender);
        friend.setReceiver(receiver);
        friend.setRequest_time(LocalDateTime.now());

        friendRepository.save(friend);
    }

    public void acceptFriend(int senderId, int receiverId) {
        Friend friend = friendRepository.findByFriendId(new FriendId(senderId, receiverId));
        friend.setAccept_time(LocalDateTime.now());

        friendRepository.save(friend);
    }

    public List<Friend> getAllRequest(int receiverId) {
        List<Friend> friends = new ArrayList<>();
        for (Friend friend: friendRepository.findByReceiverId(receiverId)) {
            if (friend.getAccept_time() == null) {
                friends.add(friend);
            }
        }
        return friends;
    }

    public void rejectFriend(int senderId, int receiverId) {
        Friend friend = friendRepository.findByFriendId(new FriendId(senderId, receiverId));
        if (friend != null) {
            friendRepository.delete(friend);
        }
    }

    public void removeFriend(int userId, int friendId) {
        Friend friend = friendRepository.findByFriendId(new FriendId(userId, friendId));
        if (friend == null) {
            friend = friendRepository.findByFriendId(new FriendId(friendId, userId));
        }
        if (friend != null) {
            friendRepository.delete(friend);
        }
    }
}
