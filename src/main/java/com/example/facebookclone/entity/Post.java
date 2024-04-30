package com.example.facebookclone.entity;

import jakarta.persistence.*;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "create_time")
    private LocalDateTime create_time;

    @Column(name = "edit_time")
    private LocalDateTime edit_time;

    @Column(name = "delete_time")
    private LocalDateTime delete_time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<PostImage> postImages;

    public Post() {
    }

    public Post(int id, String content, LocalDateTime create_time, LocalDateTime edit_time, LocalDateTime delete_time) {
        this.id = id;
        this.content = content;
        this.create_time = create_time;
        this.edit_time = edit_time;
        this.delete_time = delete_time;
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

    public LocalDateTime getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(LocalDateTime delete_time) {
        this.delete_time = delete_time;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<PostImage> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<PostImage> postImages) {
        this.postImages = postImages;
    }

    public void addPostImage(PostImage postImage) {
        if (postImages == null) {
            postImages = new ArrayList<>();
        }
        postImages.add(postImage);
    }
}
