package com.example.facebookclone.DTO;

import com.example.facebookclone.entity.Reaction_Comment_Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionCommentDTO {
    private String type;
    private Integer id_account;
    private Integer id_comment;
    public ReactionCommentDTO() {}

    public ReactionCommentDTO(Reaction_Comment_Post reactionCommentPost) {
        this.type = reactionCommentPost.getType();
        this.id_account = reactionCommentPost.getAccount().getId();
        this.id_comment = reactionCommentPost.getCommentPost().getId();
    }
}
