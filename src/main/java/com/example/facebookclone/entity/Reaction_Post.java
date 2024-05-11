package com.example.facebookclone.entity;

import com.example.facebookclone.entity.embeddedID.ReactionPostId;
import jakarta.persistence.*;

@Entity
@Table(name = "reaction_post")
public class Reaction_Post {
    @EmbeddedId
    private ReactionPostId reactionPostId;

    @Column(name = "type")
    private String type;
    @ManyToOne
    @MapsId("account_id")
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @MapsId("post_id")
    @JoinColumn(name = "post_id")
    private Post post;

    public Reaction_Post() {
    }

    public ReactionPostId getReactionPostId() {
        return reactionPostId;
    }

    public void setReactionPostId(ReactionPostId reactionPostId) {
        this.reactionPostId = reactionPostId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
