package com.example.facebookclone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notify")
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "is_read")
    private boolean is_read;

    @Column(name = "create_time")
    private LocalDateTime create_time;

    @Column(name = "notify_type")
    private String notify_type;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account notification_sender;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account notification_receiver;

    @ManyToOne
    @JoinColumn(name = "to_post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "to_comment_post_id")
    private Comment_Post receive_comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment_Post send_comment;
    public Notify() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public Account getNotification_sender() {
        return notification_sender;
    }

    public void setNotification_sender(Account notification_sender) {
        this.notification_sender = notification_sender;
    }

    public Account getNotification_receiver() {
        return notification_receiver;
    }

    public void setNotification_receiver(Account notification_receiver) {
        this.notification_receiver = notification_receiver;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }


    public Comment_Post getReceive_comment() {
        return receive_comment;
    }

    public void setReceive_comment(Comment_Post receive_comment) {
        this.receive_comment = receive_comment;
    }

    public Comment_Post getSend_comment() {
        return send_comment;
    }

    public void setSend_comment(Comment_Post send_comment) {
        this.send_comment = send_comment;
    }
}
