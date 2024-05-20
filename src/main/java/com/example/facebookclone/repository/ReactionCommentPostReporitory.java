package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Reaction_Comment_Post;
import com.example.facebookclone.entity.embeddedID.ReactionCommentPostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionCommentPostReporitory extends JpaRepository<Reaction_Comment_Post, ReactionCommentPostId> {
}
