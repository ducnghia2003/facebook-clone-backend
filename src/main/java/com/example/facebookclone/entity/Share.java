package com.example.facebookclone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "share")
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "create_time")
    private LocalDateTime create_time;

    @Column(name = "edit_time")
    private LocalDateTime edit_time;
    @Column(name = "view_mode")
    private String view_mode;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    public Share() {
    }

    public Share(String content, String view_mode, LocalDateTime create_time) {
        this.content = content;
        this.view_mode = view_mode;
        this.create_time = create_time;
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

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public LocalDateTime getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(LocalDateTime edit_time) {
        this.edit_time = edit_time;
    }

    public String getView_mode() {
        return view_mode;
    }

    public void setView_mode(String view_mode) {
        this.view_mode = view_mode;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
