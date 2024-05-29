package com.example.facebookclone.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseReaction {
    List<Integer> Like;
    List<Integer> Love;
    List<Integer> Haha;
    List<Integer> Wow;
    List<Integer> Sorry;
    List<Integer> Angry;
    List<Integer> Care;

    public ResponseReaction() {}

    public void addLike(Integer id) {
        if(Like == null) {
            Like = new ArrayList<Integer>();
        }
        Like.add(id);
    }
    public void addLove(Integer id) {
        if(Love == null) {
            Love = new ArrayList<Integer>();
        }
        Love.add(id);
    }
    public void addHaha(Integer id) {
        if(Haha == null) {
            Haha = new ArrayList<Integer>();
        }
        Haha.add(id);
    }
    public void addWow(Integer id) {
        if(Wow == null) {
            Wow = new ArrayList<Integer>();
        }
        Wow.add(id);
    }
    public void addSorry(Integer id) {
        if(Sorry == null) {
            Sorry = new ArrayList<Integer>();
        }
        Sorry.add(id);
    }
    public void addAngry(Integer id) {
        if(Angry == null) {
            Angry = new ArrayList<Integer>();
        }
        Angry.add(id);
    }
    public void addCare(Integer id) {
        if(Care == null) {
            Care = new ArrayList<Integer>();
        }
        Care.add(id);
    }
}
