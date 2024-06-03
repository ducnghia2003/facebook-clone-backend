package com.example.facebookclone.model;

import com.example.facebookclone.DTO.UserProfileDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseReaction {
    List<UserProfileDTO> Like;
    List<UserProfileDTO> Love;
    List<UserProfileDTO> Haha;
    List<UserProfileDTO> Wow;
    List<UserProfileDTO> Sorry;
    List<UserProfileDTO> Angry;
    List<UserProfileDTO > Care;

    public ResponseReaction() {}

    public void addLike(UserProfileDTO user) {
        if(Like == null) {
            Like = new ArrayList<UserProfileDTO>();
        }
        Like.add(user);
    }
    public void addLove(UserProfileDTO user) {
        if(Love == null) {
            Love = new ArrayList<UserProfileDTO>();
        }
        Love.add(user);
    }
    public void addHaha(UserProfileDTO user) {
        if(Haha == null) {
            Haha = new ArrayList<UserProfileDTO>();
        }
        Haha.add(user);
    }
    public void addWow(UserProfileDTO user) {
        if(Wow == null) {
            Wow = new ArrayList<UserProfileDTO>();
        }
        Wow.add(user);
    }
    public void addSorry(UserProfileDTO user) {
        if(Sorry == null) {
            Sorry = new ArrayList<UserProfileDTO>();
        }
        Sorry.add(user);
    }
    public void addAngry(UserProfileDTO user) {
        if(Angry == null) {
            Angry = new ArrayList<UserProfileDTO>();
        }
        Angry.add(user);
    }
    public void addCare(UserProfileDTO user) {
        if(Care == null) {
            Care = new ArrayList<UserProfileDTO>();
        }
        Care.add(user);
    }
}
