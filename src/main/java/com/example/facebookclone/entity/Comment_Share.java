package com.example.facebookclone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment_share")
public class Comment_Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "create_time")
    private LocalDateTime create_time;

    @Column(name = "edit_time")
    private LocalDateTime edit_time;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Account account_tag;
    @ManyToOne
    @JoinColumn(name = "share_id")
    private Share share;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "to_comment_id")
    private List<Comment_Share> answers;

    public Comment_Share() {
    }
    public Comment_Share(String content, LocalDateTime create_time) {
        this.content = content;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public List<Comment_Share> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Comment_Share> answers) {
        this.answers = answers;
    }
    public void addAnswer(Comment_Share answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answer);
    }

    public Account getAccount_tag() {
        return account_tag;
    }

    public void setAccount_tag(Account account_tag) {
        this.account_tag = account_tag;
    }
}
