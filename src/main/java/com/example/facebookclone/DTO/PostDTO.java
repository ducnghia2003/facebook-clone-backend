package com.example.facebookclone.DTO;

import com.example.facebookclone.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostDTO {
    private int id;

    private String content;

    private LocalDateTime create_time;

    private LocalDateTime edit_time;

    private List<PostImageDTO> postImages;

    private String view_mode;

    private int reaction_quantity;

    private int comment_quantity;

    private int share_quantity;
    private int id_account;
    public PostDTO() {}
    public PostDTO(int id, String content, LocalDateTime create_time, LocalDateTime edit_time, List<PostImageDTO> postImages) {
        this.id = id;
        this.content = content;
        this.create_time = create_time;
        this.edit_time = edit_time;
        this.postImages = postImages;
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.create_time = post.getCreate_time();
        this.edit_time = post.getEdit_time();
        this.postImages = (post.getPostImages() != null) ? post.getPostImages().stream().map(PostImageDTO::new).collect(Collectors.toList()) : List.of();
        this.view_mode = post.getView_mode();
        this.reaction_quantity = post.getReaction_quantity();
        this.comment_quantity = post.getComment_quantity();
        this.share_quantity = post.getShare_quantity();
        this.id_account = post.getAccount().getId();
    }
}
