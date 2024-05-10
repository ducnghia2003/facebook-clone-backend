package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByAccount(Account account);
}
