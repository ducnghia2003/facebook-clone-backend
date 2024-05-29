package com.example.facebookclone.service;

import com.example.facebookclone.DTO.ReactionCommentDTO;
import com.example.facebookclone.DTO.ReactionPostDTO;
import com.example.facebookclone.entity.Comment_Post;
import com.example.facebookclone.entity.Reaction_Comment_Post;
import com.example.facebookclone.entity.Reaction_Post;
import com.example.facebookclone.entity.embeddedID.ReactionCommentPostId;
import com.example.facebookclone.model.ResponseReaction;
import com.example.facebookclone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionCommentPostService {
    @Autowired
    private ReactionCommentPostReporitory reactionCommentPostReporitory;
    @Autowired
    private CommentPostRepository commentPostRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ResponseReaction getReactionsByCommentId(Integer id) {
        ResponseReaction responseReaction = new ResponseReaction();
        Optional<Comment_Post> commentPost = commentPostRepository.findById(id);
        commentPost.get().getReactionCommentPosts().stream().forEach(reactionCommentPost -> {
            if(reactionCommentPost.getType().equals("LOVE"))  responseReaction.addLove(reactionCommentPost.getReactionCommentPostId().getAccount_id());
            if(reactionCommentPost.getType().equals("LIKE"))  responseReaction.addLike(reactionCommentPost.getReactionCommentPostId().getAccount_id());
            if(reactionCommentPost.getType().equals("WOW"))  responseReaction.addWow(reactionCommentPost.getReactionCommentPostId().getAccount_id());
            if(reactionCommentPost.getType().equals("HAHA"))  responseReaction.addHaha(reactionCommentPost.getReactionCommentPostId().getAccount_id());
            if(reactionCommentPost.getType().equals("SORRY"))  responseReaction.addSorry(reactionCommentPost.getReactionCommentPostId().getAccount_id());
            if(reactionCommentPost.getType().equals("ANGRY"))  responseReaction.addAngry(reactionCommentPost.getReactionCommentPostId().getAccount_id());
            if(reactionCommentPost.getType().equals("CARE"))  responseReaction.addCare(reactionCommentPost.getReactionCommentPostId().getAccount_id());
        });
        return responseReaction;
    }

//    public List<ReactionDTO> getReactionsByPostId(Integer id) {
//        Optional<Post> foundPost = postRepository.findById(id);
//        return foundPost.get().getReactionPosts().stream().map(ReactionDTO::new).collect(Collectors.toList());
//    }

    public ReactionCommentDTO updateReactionCommentPost(Integer id_account, Integer id_comment, String type) {
        ReactionCommentPostId reactionCommentPostId = new ReactionCommentPostId(id_account, id_comment);
        Optional<Reaction_Comment_Post> reactionCommentPost = reactionCommentPostReporitory.findById(reactionCommentPostId);
        if(reactionCommentPost.isPresent()) {
            Optional<Comment_Post> commentPost = commentPostRepository.findById(id_comment);
            if(type.equals("NONE")) {
                commentPost.get().decreaseReaction_quantity();
            } else {
                if(reactionCommentPost.get().getType().equals("NONE")) commentPost.get().increaseReaction_quantity();
            }
            reactionCommentPost.get().setCommentPost(commentPost.get());
            reactionCommentPost.get().setType(type);
            return new ReactionCommentDTO(reactionCommentPostReporitory.save(reactionCommentPost.get()));
        } else {
            Reaction_Comment_Post newReactionComment = new Reaction_Comment_Post();
            newReactionComment.setReactionCommentPostId(reactionCommentPostId);
            newReactionComment.setType(type);
            newReactionComment.setAccount(accountRepository.findById(id_account).get());
            Optional<Comment_Post> commentPost = commentPostRepository.findById(id_comment);
            commentPost.get().increaseReaction_quantity();
            newReactionComment.setCommentPost(commentPost.get());
            return new ReactionCommentDTO(reactionCommentPostReporitory.save(newReactionComment));
        }
    }

    public ReactionCommentDTO getReactionToCommentPost(int userId, int commentId) {
        ReactionCommentPostId reactionCommentPostId = new ReactionCommentPostId(userId, commentId);
        Optional<Reaction_Comment_Post> reactionCommentPost = reactionCommentPostReporitory.findById(reactionCommentPostId);

        return reactionCommentPost.map(ReactionCommentDTO::new).orElse(null);
    }
}
