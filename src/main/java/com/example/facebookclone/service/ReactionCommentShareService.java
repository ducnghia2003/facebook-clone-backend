package com.example.facebookclone.service;

import com.example.facebookclone.DTO.ReactionCommentDTO;
import com.example.facebookclone.entity.Comment_Post;
import com.example.facebookclone.entity.Comment_Share;
import com.example.facebookclone.entity.Reaction_Comment_Post;
import com.example.facebookclone.entity.Reaction_Comment_Share;
import com.example.facebookclone.entity.embeddedID.ReactionCommentPostId;
import com.example.facebookclone.entity.embeddedID.ReactionCommentShareId;
import com.example.facebookclone.model.ResponseReaction;
import com.example.facebookclone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionCommentShareService {
    @Autowired
    private ReactionCommentShareRepository reactionCommentShareRepository;
    @Autowired
    private CommentShareRepository commentShareRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ResponseReaction getReactionsByCommentId(Integer id) {
        ResponseReaction responseReaction = new ResponseReaction();
        Optional<Comment_Share> commentShare = commentShareRepository.findById(id);
        commentShare.get().getReactionCommentShares().stream().forEach(reactionCommentShare -> {
            if(reactionCommentShare.getType().equals("LOVE"))  responseReaction.addLove(reactionCommentShare.getReactionCommentShareId().getAccount_id());
            if(reactionCommentShare.getType().equals("LIKE"))  responseReaction.addLike(reactionCommentShare.getReactionCommentShareId().getAccount_id());
            if(reactionCommentShare.getType().equals("WOW"))  responseReaction.addWow(reactionCommentShare.getReactionCommentShareId().getAccount_id());
            if(reactionCommentShare.getType().equals("HAHA"))  responseReaction.addHaha(reactionCommentShare.getReactionCommentShareId().getAccount_id());
            if(reactionCommentShare.getType().equals("SORRY"))  responseReaction.addSorry(reactionCommentShare.getReactionCommentShareId().getAccount_id());
            if(reactionCommentShare.getType().equals("ANGRY"))  responseReaction.addAngry(reactionCommentShare.getReactionCommentShareId().getAccount_id());
            if(reactionCommentShare.getType().equals("ADORE"))  responseReaction.addAdore(reactionCommentShare.getReactionCommentShareId().getAccount_id());
        });
        return responseReaction;
    }

    public ReactionCommentDTO updateReactionCommentShare(Integer id_account, Integer id_comment, String type) {
        ReactionCommentShareId reactionCommentShareId = new ReactionCommentShareId(id_account, id_comment);
        Optional<Reaction_Comment_Share> reactionCommentShare = reactionCommentShareRepository.findById(reactionCommentShareId);
        if(reactionCommentShare.isPresent()) {
            Optional<Comment_Share> commentShare = commentShareRepository.findById(id_comment);
            if(type.equals("NONE")) {
                commentShare.get().decreaseReaction_quantity();
            } else {
                if(reactionCommentShare.get().getType().equals("NONE")) commentShare.get().increaseReaction_quantity();
            }
            reactionCommentShare.get().setCommentShare(commentShare.get());
            reactionCommentShare.get().setType(type);
            return new ReactionCommentDTO(reactionCommentShareRepository.save(reactionCommentShare.get()));
        } else {
            Reaction_Comment_Share newReactionComment = new Reaction_Comment_Share();
            newReactionComment.setReactionCommentShareId(reactionCommentShareId);
            newReactionComment.setType(type);
            newReactionComment.setAccount(accountRepository.findById(id_account).get());
            Optional<Comment_Share> commentShare = commentShareRepository.findById(id_comment);
            commentShare.get().increaseReaction_quantity();
            newReactionComment.setCommentShare(commentShare.get());
            return new ReactionCommentDTO(reactionCommentShareRepository.save(newReactionComment));
        }
    }

    public ReactionCommentDTO getReactionToCommentShare(int userId, int commentId) {
        ReactionCommentShareId reactionCommentShareId = new ReactionCommentShareId(userId, commentId);
        Optional<Reaction_Comment_Share> reactionCommentShare = reactionCommentShareRepository.findById(reactionCommentShareId);

        return reactionCommentShare.map(ReactionCommentDTO::new).orElse(null);
    }
}
