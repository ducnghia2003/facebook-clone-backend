package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Reaction_Comment_Share;
import com.example.facebookclone.entity.embeddedID.ReactionCommentShareId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionCommentShareRepository extends JpaRepository<Reaction_Comment_Share, ReactionCommentShareId> {
}
