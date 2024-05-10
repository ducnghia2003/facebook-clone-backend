package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Post;
import com.example.facebookclone.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Integer> {
    List<PostImage> findAllBypost(Post post);
}
