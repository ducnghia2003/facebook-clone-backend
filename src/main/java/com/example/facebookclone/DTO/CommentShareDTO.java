package com.example.facebookclone.DTO;

import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Comment_Post;
import com.example.facebookclone.entity.Comment_Share;
import com.example.facebookclone.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentShareDTO {
    private Integer id;
    private String content;
    private LocalDateTime create_time;
    private LocalDateTime edit_time;
    private String image;
    private Integer id_account;
    private Integer account_tag;
    private Integer id_share;
    private Integer reaction_quantity;
    private String reaction;
    private List<CommentShareDTO> answers;

    public CommentShareDTO() {
    }

    public CommentShareDTO(Comment_Share commentShare) {
        this.id = commentShare.getId();
        this.content = commentShare.getContent();
        this.create_time = commentShare.getCreate_time();
        this.edit_time = commentShare.getEdit_time();
        this.image = commentShare.getImage();
        this.id_account = commentShare.getAccount().getId();
        this.reaction_quantity = commentShare.getReaction_quantity();
        this.account_tag = (commentShare.getAccount_tag() != null) ? commentShare.getAccount_tag().getId() : null;
        this.id_share = (commentShare.getShare() != null) ? commentShare.getShare().getId() : null;
        this.answers = (commentShare.getAnswers() != null) ? commentShare.getAnswers().stream().map(CommentShareDTO::new).collect(Collectors.toList()) : List.of();
    }
}
