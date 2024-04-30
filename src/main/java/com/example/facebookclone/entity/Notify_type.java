package com.example.facebookclone.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "notify_type")
public class Notify_type {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "notify_type")
    private List<Notify> notifies;

    public Notify_type() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Notify> getNotifies() {
        return notifies;
    }

    public void setNotifies(List<Notify> notifies) {
        this.notifies = notifies;
    }

    public void addNotify(Notify notify) {
        if (notifies == null) {
            notifies = new java.util.ArrayList<>();
        }
        notifies.add(notify);
        notify.setNotify_type(this);
    }
}
