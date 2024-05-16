package com.example.facebookclone.DTO;

import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Comment_Post;
import com.example.facebookclone.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentPostDTO {
    private Integer id;
    private String content;
    private LocalDateTime create_time;
    private LocalDateTime edit_time;
    private String image;
    private Integer id_account;
    private Integer account_tag;
    private Integer id_post;
    private List<CommentPostDTO> answers;

    public CommentPostDTO() {
    }

    public CommentPostDTO(Comment_Post commentPost) {
        this.id = commentPost.getId();
        this.content = commentPost.getContent();
        this.create_time = commentPost.getCreate_time();
        this.edit_time = commentPost.getEdit_time();
        this.image = commentPost.getImage();
        this.id_account = commentPost.getAccount().getId();
        this.account_tag = (commentPost.getAccount_tag() != null) ? commentPost.getAccount_tag().getId() : null;
        this.id_post = (commentPost.getPost() != null) ? commentPost.getPost().getId() : null;
        this.answers = (commentPost.getAnswers() != null) ? commentPost.getAnswers().stream().map(CommentPostDTO::new).collect(Collectors.toList()) : List.of();
    }
}
