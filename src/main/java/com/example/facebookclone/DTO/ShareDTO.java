package com.example.facebookclone.DTO;

import com.example.facebookclone.entity.Post;
import com.example.facebookclone.entity.Share;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ShareDTO {
    private int id;

    private String content;

    private LocalDateTime create_time;

    private LocalDateTime edit_time;


    private PostDTO post;

    private String view_mode;
    public ShareDTO() {}

    public ShareDTO(Share share) {
        this.id = share.getId();
        this.content = share.getContent();
        this.create_time = share.getCreate_time();
        this.edit_time = share.getEdit_time();
        this.view_mode = share.getView_mode();
        this.post = new PostDTO(share.getPost());
    }
}