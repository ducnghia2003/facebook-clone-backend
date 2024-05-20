package com.example.facebookclone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "reaction_quantity")
    private int reaction_quantity;
    @Column(name = "comment_quantity")
    private int comment_quantity;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;
    @OneToMany(mappedBy = "share", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<Comment_Share> comments;

    @OneToMany(mappedBy = "share", cascade = CascadeType.ALL)
    private List<Reaction_Share> reactionShares;
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
    public int getReaction_quantity() {
        return reaction_quantity;
    }

    public void setReaction_quantity(int reaction_quantity) {
        this.reaction_quantity = reaction_quantity;
    }

    public void increaseReaction_quantity() {
        this.reaction_quantity = this.reaction_quantity + 1;
    }
    public void decreaseReaction_quantity() {
        this.reaction_quantity = this.reaction_quantity - 1;
    }
    public int getComment_quantity() {
        if (comments == null) {
            return 0;
        }
        else {
            int count = comments.stream().mapToInt(comment -> comment.getAnswers().size()).sum();
            this.comment_quantity = count + comments.size();
            return comment_quantity;
        }
    }

    public void setComment_quantity(int comment_quantity) {
        this.comment_quantity = comment_quantity;
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

    public List<Comment_Share> getComments() {
        return comments;
    }

    public void setComments(List<Comment_Share> comments) {
        this.comments = comments;
    }

    public List<Reaction_Share> getReactionShares() {
        return reactionShares;
    }

    public void setReactionShares(List<Reaction_Share> reactionShares) {
        this.reactionShares = reactionShares;
    }
}
