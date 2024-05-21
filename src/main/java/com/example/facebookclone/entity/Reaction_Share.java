package com.example.facebookclone.entity;

import com.example.facebookclone.entity.embeddedID.ReactionPostId;
import com.example.facebookclone.entity.embeddedID.ReactionShareId;
import jakarta.persistence.*;

@Entity
@Table(name = "reaction_share")
public class Reaction_Share {
    @EmbeddedId
    private ReactionShareId reactionShareId;

    @Column(name = "type")
    private String type;
    @ManyToOne
    @MapsId("account_id")
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @MapsId("share_id")
    @JoinColumn(name = "share_id")
    private Share share;

    public Reaction_Share() {
    }

    public ReactionShareId getReactionShareId() {
        return reactionShareId;
    }

    public void setReactionShareId(ReactionShareId reactionShareId) {
        this.reactionShareId = reactionShareId;
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

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }
}
