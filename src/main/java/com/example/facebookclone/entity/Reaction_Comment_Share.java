package com.example.facebookclone.entity;

import com.example.facebookclone.entity.embeddedID.ReactionCommentPostId;
import com.example.facebookclone.entity.embeddedID.ReactionCommentShareId;
import jakarta.persistence.*;

@Entity
@Table(name = "reaction_comment_share")
public class Reaction_Comment_Share {
    @EmbeddedId
    private ReactionCommentShareId reactionCommentShareId;

    @Column(name = "type")
    private String type;
    @ManyToOne
    @MapsId("account_id")
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @MapsId("comment_id")
    @JoinColumn(name = "comment_id")
    private Comment_Share commentShare;

    public Reaction_Comment_Share() {
    }

    public ReactionCommentShareId getReactionCommentShareId() {
        return reactionCommentShareId;
    }

    public void setReactionCommentShareId(ReactionCommentShareId reactionCommentShareId) {
        this.reactionCommentShareId = reactionCommentShareId;
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

    public Comment_Share getCommentShare() {
        return commentShare;
    }

    public void setCommentShare(Comment_Share commentShare) {
        this.commentShare = commentShare;
    }
}
