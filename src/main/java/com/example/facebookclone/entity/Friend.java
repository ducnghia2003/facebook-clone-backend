package com.example.facebookclone.entity;

import com.example.facebookclone.entity.embeddedID.FriendId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend")
public class Friend {
    @EmbeddedId
    private FriendId friendId;

    @ManyToOne
    @MapsId("senderId")
    @JoinColumn(name = "from_account_id")
    private Account sender;

    @ManyToOne
    @MapsId("receiverId")
    @JoinColumn(name = "to_account_id")
    private Account receiver;

    @Column(name = "friend_request_time")
    private LocalDateTime request_time;

    @Column(name = "friend_time")
    private LocalDateTime accept_time;

    public Friend() {
    }

    public FriendId getFriendId() {
        return friendId;
    }

    public void setFriendId(FriendId friendId) {
        this.friendId = friendId;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getRequest_time() {
        return request_time;
    }

    public void setRequest_time(LocalDateTime request_time) {
        this.request_time = request_time;
    }

    public LocalDateTime getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(LocalDateTime accept_time) {
        this.accept_time = accept_time;
    }
}
