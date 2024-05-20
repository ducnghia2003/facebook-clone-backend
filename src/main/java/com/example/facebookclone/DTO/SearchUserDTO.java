package com.example.facebookclone.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserDTO {
    private int id;
    private String name;
    private String avatar;
    private String status;

    public SearchUserDTO(int id, String name, String avatar, String status) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.status = status;
    }

    public SearchUserDTO(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
