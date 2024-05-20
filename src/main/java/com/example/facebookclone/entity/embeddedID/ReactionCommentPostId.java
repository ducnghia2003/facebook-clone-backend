package com.example.facebookclone.entity.embeddedID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ReactionCommentPostId implements Serializable {
    @Column(name = "account_id")
    private int account_id;
    @Column(name = "comment_id")
    private int comment_id;

    public ReactionCommentPostId() {
    }

    public ReactionCommentPostId(int account_id, int comment_id) {
        this.account_id = account_id;
        this.comment_id = comment_id;
    }
}
