package com.example.facebookclone.service;

import com.example.facebookclone.DTO.ReactionDTO;
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
            if(reactionPost.getType().equals("LOVE"))  responseReaction.addLove(reactionPost.getReactionPostId().getAccount_id());
            if(reactionPost.getType().equals("LIKE"))  responseReaction.addLike(reactionPost.getReactionPostId().getAccount_id());
            if(reactionPost.getType().equals("WOW"))  responseReaction.addWow(reactionPost.getReactionPostId().getAccount_id());
            if(reactionPost.getType().equals("HAHA"))  responseReaction.addHaha(reactionPost.getReactionPostId().getAccount_id());
            if(reactionPost.getType().equals("SORRY"))  responseReaction.addSorry(reactionPost.getReactionPostId().getAccount_id());
            if(reactionPost.getType().equals("ANGRY"))  responseReaction.addAngry(reactionPost.getReactionPostId().getAccount_id());
            if(reactionPost.getType().equals("ADORE"))  responseReaction.addAdore(reactionPost.getReactionPostId().getAccount_id());
        });
        return responseReaction;
    }

//    public List<ReactionDTO> getReactionsByPostId(Integer id) {
//        Optional<Post> foundPost = postRepository.findById(id);
//        return foundPost.get().getReactionPosts().stream().map(ReactionDTO::new).collect(Collectors.toList());
//    }

    public ReactionDTO updateReactionPost(Integer id_account, Integer id_post, String type) {
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
            return new ReactionDTO(reactionPostRepository.save(reactionPost.get()));
        } else {
            Reaction_Post newReactionPost = new Reaction_Post();
            newReactionPost.setReactionPostId(reactionPostId);
            newReactionPost.setType(type);
            newReactionPost.setAccount(accountRepository.findById(id_account).get());
            Optional<Post> post = postRepository.findById(id_post);
            post.get().increaseReaction_quantity();
            newReactionPost.setPost(post.get());
            return new ReactionDTO(reactionPostRepository.save(newReactionPost));
        }
    }
}
