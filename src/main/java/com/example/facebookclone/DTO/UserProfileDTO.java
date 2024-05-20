package com.example.facebookclone.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserProfileDTO {
    private int id;
    private String profile_name;
    private String come_from;
    private String live_at;
    private String avatar;
    private String cover_image;
    private String description;
    private LocalDate birth_date;
    private LocalDateTime create_time;
    private int total_friend;

    public UserProfileDTO() {
    }

    public UserProfileDTO(int id, String profile_name, String come_from, String live_at, String avatar, String cover_image, String description, LocalDate birth_date, LocalDateTime create_time, int total_friend) {
        this.id = id;
        this.profile_name = profile_name;
        this.come_from = come_from;
        this.live_at = live_at;
        this.avatar = avatar;
        this.cover_image = cover_image;
        this.description = description;
        this.birth_date = birth_date;
        this.create_time = create_time;
        this.total_friend = total_friend;
    }
}
