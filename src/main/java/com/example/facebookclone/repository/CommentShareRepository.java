package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Comment_Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentShareRepository extends JpaRepository<Comment_Share, Integer> {
}
