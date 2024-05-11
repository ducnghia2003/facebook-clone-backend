package com.example.facebookclone.entity;

import jakarta.persistence.*;

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

    @Column(name = "view_mode")
    private String view_mode;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImages;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<Comment_Post> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Notify> notifies;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Reaction_Post> reactionPosts;
    public Post() {
    }

    public Post(int id, String content, LocalDateTime create_time, LocalDateTime edit_time) {
        this.id = id;
        this.content = content;
        this.create_time = create_time;
        this.edit_time = edit_time;
    }

    public Post(String content,String view_mode, LocalDateTime create_time) {
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

    public List<Comment_Post> getComments() {
        return comments;
    }

    public void setComments(List<Comment_Post> comments) {
        this.comments = comments;
    }

    public List<Notify> getNotifies() {
        return notifies;
    }

    public void setNotifies(List<Notify> notifies) {
        this.notifies = notifies;
    }



    public List<Reaction_Post> getReactionPosts() {
        return reactionPosts;
    }

    public void setReactionPosts(List<Reaction_Post> reactionPosts) {
        this.reactionPosts = reactionPosts;
    }
}
