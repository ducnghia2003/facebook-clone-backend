package com.example.facebookclone.DTO;

import com.example.facebookclone.entity.Notify;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotifyDTO {
    private Integer id;
    private String avatar_sender;
    private Integer receiver_id;
    private Integer to_post_id;
    private Integer to_comment_post_id;
    private boolean is_read;
    private LocalDateTime create_time;
    private String content;
    private String type;

    public NotifyDTO() {}

    public NotifyDTO(Notify notify) {
        this.id = notify.getId();
        this.avatar_sender = notify.getNotification_sender().getAvatar();
        this.receiver_id = notify.getNotification_receiver().getId();
        this.to_post_id = (notify.getPost() != null) ? notify.getPost().getId() : null;
        this.to_comment_post_id = (notify.getComment_post() != null) ? notify.getComment_post().getId() : null;
        this.is_read = notify.isIs_read();
        this.create_time = notify.getCreate_time();
        this.content = notify.getContent();
        this.type = notify.getNotify_type();
    }
}
