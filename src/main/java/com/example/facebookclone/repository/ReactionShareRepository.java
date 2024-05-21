package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Reaction_Share;
import com.example.facebookclone.entity.embeddedID.ReactionShareId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionShareRepository extends JpaRepository<Reaction_Share, ReactionShareId> {
}
