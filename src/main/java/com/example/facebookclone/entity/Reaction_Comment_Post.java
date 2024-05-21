package com.example.facebookclone.entity;

import com.example.facebookclone.entity.embeddedID.ReactionCommentPostId;
import jakarta.persistence.*;

@Entity
@Table(name = "reaction_comment_post")
public class Reaction_Comment_Post {
    @EmbeddedId
    private ReactionCommentPostId reactionCommentPostId;

    @Column(name = "type")
    private String type;
    @ManyToOne
    @MapsId("account_id")
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @MapsId("comment_id")
    @JoinColumn(name = "comment_id")
    private Comment_Post commentPost;

    public Reaction_Comment_Post() {
    }

    public ReactionCommentPostId getReactionCommentPostId() {
        return reactionCommentPostId;
    }

    public void setReactionCommentPostId(ReactionCommentPostId reactionCommentPostId) {
        this.reactionCommentPostId = reactionCommentPostId;
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

    public Comment_Post getCommentPost() {
        return commentPost;
    }

    public void setCommentPost(Comment_Post commentPost) {
        this.commentPost = commentPost;
    }
}
