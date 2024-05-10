package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Reaction_Post;
import com.example.facebookclone.entity.embeddedID.ReactionPostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionPostRepository extends JpaRepository<Reaction_Post, ReactionPostId> {

}
