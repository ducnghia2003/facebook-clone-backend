package com.example.facebookclone.service;

import com.example.facebookclone.DTO.ReactionPostDTO;
import com.example.facebookclone.DTO.ReactionShareDTO;
import com.example.facebookclone.entity.Post;
import com.example.facebookclone.entity.Reaction_Post;
import com.example.facebookclone.entity.Reaction_Share;
import com.example.facebookclone.entity.Share;
import com.example.facebookclone.entity.embeddedID.ReactionPostId;
import com.example.facebookclone.entity.embeddedID.ReactionShareId;
import com.example.facebookclone.model.ResponseReaction;
import com.example.facebookclone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionShareService {
    @Autowired
    private ReactionShareRepository reactionShareRepository;
    @Autowired
    private ShareRepository shareRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ResponseReaction getReactionsByShareId(Integer id) {
        ResponseReaction responseReaction = new ResponseReaction();
        Optional<Share> foundShare = shareRepository.findById(id);
        foundShare.get().getReactionShares().stream().forEach(reactionShare -> {
            if(reactionShare.getType().equals("LOVE"))  responseReaction.addLove(reactionShare.getReactionShareId().getAccount_id());
            if(reactionShare.getType().equals("LIKE"))  responseReaction.addLike(reactionShare.getReactionShareId().getAccount_id());
            if(reactionShare.getType().equals("WOW"))  responseReaction.addWow(reactionShare.getReactionShareId().getAccount_id());
            if(reactionShare.getType().equals("HAHA"))  responseReaction.addHaha(reactionShare.getReactionShareId().getAccount_id());
            if(reactionShare.getType().equals("SORRY"))  responseReaction.addSorry(reactionShare.getReactionShareId().getAccount_id());
            if(reactionShare.getType().equals("ANGRY"))  responseReaction.addAngry(reactionShare.getReactionShareId().getAccount_id());
        });
        return responseReaction;
    }

//    public List<ReactionDTO> getReactionsByPostId(Integer id) {
//        Optional<Post> foundPost = postRepository.findById(id);
//        return foundPost.get().getReactionPosts().stream().map(ReactionDTO::new).collect(Collectors.toList());
//    }

    public ReactionShareDTO updateReactionShare(Integer id_account, Integer id_share, String type) {
        ReactionShareId reactionShareId = new ReactionShareId(id_account, id_share);
        Optional<Reaction_Share> reactionShare = reactionShareRepository.findById(reactionShareId);
        if(reactionShare.isPresent()) {
            Optional<Share> share = shareRepository.findById(id_share);
            if(type.equals("NONE")) {
                share.get().decreaseReaction_quantity();
            } else {
                if(reactionShare.get().getType().equals("NONE")) share.get().increaseReaction_quantity();
            }
            reactionShare.get().setShare(share.get());
            reactionShare.get().setType(type);
            return new ReactionShareDTO(reactionShareRepository.save(reactionShare.get()));
        } else {
            Reaction_Share newReactionShare = new Reaction_Share();
            newReactionShare.setReactionShareId(reactionShareId);
            newReactionShare.setType(type);
            newReactionShare.setAccount(accountRepository.findById(id_account).get());
            Optional<Share> share = shareRepository.findById(id_share);
            share.get().increaseReaction_quantity();
            newReactionShare.setShare(share.get());
            return new ReactionShareDTO(reactionShareRepository.save(newReactionShare));
        }
    }

    public ReactionShareDTO getReactionToShare(int userId, int shareid) {
        ReactionShareId reactionShareId = new ReactionShareId(userId, shareid);
        Optional<Reaction_Share> reactionShare = reactionShareRepository.findById(reactionShareId);

        return reactionShare.map(ReactionShareDTO::new).orElse(null);
    }
}
