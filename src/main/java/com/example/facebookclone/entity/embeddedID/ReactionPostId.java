package com.example.facebookclone.entity.embeddedID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ReactionPostId implements Serializable {
    @Column(name = "account_id")
    private int account_id;
    @Column(name = "post_id")
    private int post_id;

    public ReactionPostId() {
    }

    public ReactionPostId(int account_id, int post_id) {
        this.account_id = account_id;
        this.post_id = post_id;
    }
}
