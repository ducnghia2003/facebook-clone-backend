package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Comment_Post;
import com.example.facebookclone.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentPostRepository extends JpaRepository<Comment_Post, Integer> {
    List<Comment_Post> findAllBypost(Post post);
}
