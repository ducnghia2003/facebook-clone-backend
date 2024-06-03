package com.example.facebookclone.service;

import com.example.facebookclone.DTO.ReactionPostDTO;
import com.example.facebookclone.DTO.UserProfileDTO;
import com.example.facebookclone.model.ResponseReaction;
import com.example.facebookclone.entity.Post;
import com.example.facebookclone.entity.Reaction_Post;
import com.example.facebookclone.entity.embeddedID.ReactionPostId;
import com.example.facebookclone.repository.AccountRepository;
import com.example.facebookclone.repository.PostRepository;
import com.example.facebookclone.repository.ReactionPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionPostService {
    @Autowired
    private ReactionPostRepository reactionPostRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ResponseReaction getReactionsByPostId(Integer id) {
        ResponseReaction responseReaction = new ResponseReaction();
        Optional<Post> foundPost = postRepository.findById(id);
        foundPost.get().getReactionPosts().stream().forEach(reactionPost -> {
            if(reactionPost.getType().equals("LOVE"))  responseReaction.addLove(new UserProfileDTO(reactionPost.getAccount()));
            else if(reactionPost.getType().equals("LIKE"))  responseReaction.addLike(new UserProfileDTO(reactionPost.getAccount()));
            else if(reactionPost.getType().equals("WOW"))  responseReaction.addWow(new UserProfileDTO(reactionPost.getAccount()));
            else if(reactionPost.getType().equals("HAHA"))  responseReaction.addHaha(new UserProfileDTO(reactionPost.getAccount()));
            else if(reactionPost.getType().equals("SORRY"))  responseReaction.addSorry(new UserProfileDTO(reactionPost.getAccount()));
            else if(reactionPost.getType().equals("ANGRY"))  responseReaction.addAngry(new UserProfileDTO(reactionPost.getAccount()));
            else if (reactionPost.getType().equals("CARE"))  responseReaction.addCare(new UserProfileDTO(reactionPost.getAccount()));
        });
        return responseReaction;
    }

//    public List<ReactionDTO> getReactionsByPostId(Integer id) {
//        Optional<Post> foundPost = postRepository.findById(id);
//        return foundPost.get().getReactionPosts().stream().map(ReactionDTO::new).collect(Collectors.toList());
//    }

    public ReactionPostDTO updateReactionPost(Integer id_account, Integer id_post, String type) {
        ReactionPostId reactionPostId = new ReactionPostId(id_account, id_post);
        Optional<Reaction_Post> reactionPost = reactionPostRepository.findById(reactionPostId);
        if(reactionPost.isPresent()) {
            Optional<Post> post = postRepository.findById(id_post);
            if(type.equals("NONE")) {
                post.get().decreaseReaction_quantity();
            } else {
                if(reactionPost.get().getType().equals("NONE")) post.get().increaseReaction_quantity();
            }
            reactionPost.get().setPost(post.get());
            reactionPost.get().setType(type);
            return new ReactionPostDTO(reactionPostRepository.save(reactionPost.get()));
        } else {
            Reaction_Post newReactionPost = new Reaction_Post();
            newReactionPost.setReactionPostId(reactionPostId);
            newReactionPost.setType(type);
            newReactionPost.setAccount(accountRepository.findById(id_account).get());
            Optional<Post> post = postRepository.findById(id_post);
            post.get().increaseReaction_quantity();
            newReactionPost.setPost(post.get());
            return new ReactionPostDTO(reactionPostRepository.save(newReactionPost));
        }
    }

    public ReactionPostDTO getReactionToPost(int userId, int postId) {
        ReactionPostId reactionPostId = new ReactionPostId(userId, postId);
        Optional<Reaction_Post> reactionPost = reactionPostRepository.findById(reactionPostId);

        return reactionPost.map(ReactionPostDTO::new).orElse(null);
    }
}
