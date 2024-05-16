package com.example.facebookclone.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestDTO {
    private int id;
    private String name;
    private String avatar;
    private String time;
    public FriendRequestDTO() {}

    public FriendRequestDTO(int id, String name, String avatar, String time) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.time = time;
    }
}