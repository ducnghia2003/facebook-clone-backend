package com.example.facebookclone.entity.embeddedID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ReactionShareId implements Serializable {
    @Column(name = "account_id")
    private int account_id;
    @Column(name = "share_id")
    private int share_id;

    public ReactionShareId() {
    }

    public ReactionShareId(int account_id, int share_id) {
        this.account_id = account_id;
        this.share_id = share_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id, share_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ReactionShareId that = (ReactionShareId) obj;
        return account_id == that.account_id && share_id == that.share_id;
    }
}
