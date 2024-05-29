package com.example.facebookclone.DTO;

import com.example.facebookclone.entity.Account;
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

    public UserProfileDTO(Account account) {
        this.id = account.getId();
        this.profile_name = account.getProfile_name();
        this.avatar = account.getAvatar();
        this.come_from = account.getCome_from();
        this.live_at = account.getLive_at();
        this.cover_image = account.getCoverImage();
        this.description = account.getDescription();
        this.birth_date = account.getBrithdate();
        this.create_time = account.getCreate_time();
        this.total_friend = (int) (account.getFriends().stream().filter(friend -> friend.getAccept_time() != null).count() +
                account.getFriendOf().stream().filter(friend -> friend.getAccept_time() != null).count());
    }
}
