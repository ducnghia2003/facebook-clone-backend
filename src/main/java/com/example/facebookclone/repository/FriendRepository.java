package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Friend;
import com.example.facebookclone.entity.embeddedID.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    Friend findByFriendId(FriendId friendId);
    List<Friend> findBySenderId(int senderId);
    List<Friend> findByReceiverId(int receiverId);
}
