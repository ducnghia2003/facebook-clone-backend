package com.example.facebookclone.entity.embeddedID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendId implements Serializable {
    @Column(name = "from_account_id")
    private int senderId;

    @Column(name = "to_account_id")
    private int receiverId;


    public FriendId(int senderId, int receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public FriendId() {

    }

    @Override
    public int hashCode() {
        return Objects.hash(senderId, receiverId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FriendId friendId = (FriendId) obj;
        return senderId == friendId.senderId && receiverId == friendId.receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
}
