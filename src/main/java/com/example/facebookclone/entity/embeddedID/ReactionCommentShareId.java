package com.example.facebookclone.entity.embeddedID;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

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
}
