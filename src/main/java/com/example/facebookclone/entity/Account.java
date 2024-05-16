package com.example.facebookclone.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String profile_name;

    @Column(name = "birth_date")
    private LocalDate brithdate;

    @Column(name = "sex")
    private boolean sex;

    @Column(name = "create_time")
    private LocalDateTime create_time;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "description")
    private String description;

    @Column(name = "come_from")
    private String come_from;

    @Column(name = "live_at")
    private String live_at;

    @Column(name = "coverImage")
    private String coverImage;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Post> posts;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Share> shares;
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Friend> friends;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Friend> friendOf;

    @OneToMany(mappedBy = "notification_sender", cascade = CascadeType.ALL)
    private List<Notify> send_notifies;

    @OneToMany(mappedBy = "notification_receiver", cascade = CascadeType.ALL)
    private List<Notify> receive_notifies;

    public Account() {
    }

    public Account(int id, String username, String password, String email, String profile_name,
                   LocalDate brithdate, boolean sex, LocalDateTime create_time, String avatar,
                   String description, String come_from, String live_at, String coverImage) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.profile_name = profile_name;
        this.brithdate = brithdate;
        this.sex = sex;
        this.create_time = create_time;
        this.avatar = avatar;
        this.description = description;
        this.come_from = come_from;
        this.live_at = live_at;
        this.coverImage = coverImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public LocalDate getBrithdate() {
        return brithdate;
    }

    public void setBrithdate(LocalDate brithdate) {
        this.brithdate = brithdate;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCome_from() {
        return come_from;
    }

    public void setCome_from(String come_from) {
        this.come_from = come_from;
    }

    public String getLive_at() {
        return live_at;
    }

    public void setLive_at(String live_at) {
        this.live_at = live_at;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(post);
        post.setAccount(this);
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public void addFriend(Friend friend) {
        if (friends == null) {
            friends = new ArrayList<>();
        }
        friends.add(friend);
        friend.setSender(this);
    }

    public List<Friend> getFriendOf() {
        return friendOf;
    }

    public void setFriendOf(List<Friend> friendOf) {
        this.friendOf = friendOf;
    }

    public List<Notify> getSend_notifies() {
        return send_notifies;
    }

    public void setSend_notifies(List<Notify> send_notifies) {
        this.send_notifies = send_notifies;
    }

    public List<Notify> getReceive_notifies() {
        return receive_notifies;
    }

    public void setReceive_notifies(List<Notify> receive_notifies) {
        this.receive_notifies = receive_notifies;
    }
}
