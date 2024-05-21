package com.example.facebookclone.DTO;

import com.example.facebookclone.entity.Reaction_Post;
import com.example.facebookclone.entity.Reaction_Share;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionShareDTO {
    private String type;
    private Integer id_account;
    private Integer id_post;
    public ReactionShareDTO() {}

    public ReactionShareDTO(Reaction_Share reactionShare) {
        this.type = reactionShare.getType();
        this.id_account = reactionShare.getAccount().getId();
        this.id_post = reactionShare.getShare().getId();
    }
}
