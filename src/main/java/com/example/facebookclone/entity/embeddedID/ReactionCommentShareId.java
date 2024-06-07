package com.example.facebookclone.entity.embeddedID;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ReactionCommentShareId {
    @Column(name = "account_id")
    private int account_id;
    @Column(name = "comment_id")
    private int comment_id;

    public ReactionCommentShareId() {
    }

    public ReactionCommentShareId(int account_id, int comment_id) {
        this.account_id = account_id;
        this.comment_id = comment_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id, comment_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ReactionCommentShareId that = (ReactionCommentShareId) obj;
        return account_id == that.account_id && comment_id == that.comment_id;
    }
}
