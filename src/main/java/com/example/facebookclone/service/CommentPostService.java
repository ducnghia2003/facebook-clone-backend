package com.example.facebookclone.service;

import com.cloudinary.utils.ObjectUtils;
import com.example.facebookclone.DTO.*;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Comment_Post;
import com.example.facebookclone.entity.Post;
import com.example.facebookclone.entity.PostImage;
import com.example.facebookclone.repository.AccountRepository;
import com.example.facebookclone.repository.CommentPostRepository;
import com.example.facebookclone.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentPostService {
    @Autowired
    private CommentPostRepository commentPostRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ReactionCommentPostService reactionCommentPostService;

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public CommentPostDTO getCommentsById(int id) {
        return new CommentPostDTO(commentPostRepository.findById(id).get());
    }

    public List<CommentPostDTO> getCommentsByPost(int id, int user_id) {
        Optional<Post> post = postRepository.findById(id);
        List<CommentPostDTO> commentPostDTOS = post.get().getComments().stream().map(CommentPostDTO::new).collect(Collectors.toList());
        for(CommentPostDTO commentPostDTO: commentPostDTOS) {
            int index = commentPostDTOS.indexOf(commentPostDTO);
            ReactionCommentDTO reactionCommentDTO = reactionCommentPostService.getReactionToCommentPost(user_id, commentPostDTO.getId());
            commentPostDTO.setReaction((reactionCommentDTO != null) ? reactionCommentDTO.getType() : "NONE");

            if(commentPostDTO.getAnswers() != null) {
                List<CommentPostDTO> answers = commentPostDTO.getAnswers();
                for(CommentPostDTO answer: answers) {
                    int i = answers.indexOf(answer);
                    ReactionCommentDTO reactionCommentDTO1 = reactionCommentPostService.getReactionToCommentPost(user_id, answer.getId());
                    answer.setReaction((reactionCommentDTO1 != null) ? reactionCommentDTO1.getType() : "NONE");
                    answers.set(i, answer);
                }
                commentPostDTO.setAnswers(answers);
            }

            commentPostDTOS.set(index, commentPostDTO);
        }

        return commentPostDTOS;
    }

    public CommentPostDTO createComment(Integer id_account, Integer account_tag, Integer id_post, String content, MultipartFile image, Integer to_comment_id) {
        Optional<Account> account = accountRepository.findById(id_account);
        Comment_Post commentPost = new Comment_Post(content, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        if(id_post != null)  {
            Optional<Post> post = postRepository.findById(id_post);
            commentPost.setPost(post.get());
        }
        commentPost.setAccount(account.get());

        if(account_tag != null) {
            commentPost.setAccount_tag(accountRepository.findById(account_tag).get());
        }

        if(image != null) {
            try {
                String url = cloudinary.getInstance().uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).values().toArray()[3].toString();
                commentPost.setImage(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(to_comment_id != null) {
            Comment_Post parent = commentPostRepository.findById(to_comment_id).get();
            int depth = 2;

            while (parent.getParent() != null) {
                parent = parent.getParent();
                depth++;
            }

            Comment_Post parentPost = null;

            if (depth > 3)
                parentPost = commentPostRepository.findById(to_comment_id).get().getParent();
            else
                parentPost = commentPostRepository.findById(to_comment_id).get();

            parentPost.addAnswer(commentPost);
            commentPost.setParent(parentPost);

            commentPostRepository.save(parentPost);
            for(Comment_Post comment_post : parentPost.getAnswers()) {
                if(comment_post.getAccount() == commentPost.getAccount() && comment_post.getCreate_time() == commentPost.getCreate_time()) {
                    return new CommentPostDTO(comment_post);
                }
            }
        }

        commentPostRepository.save(commentPost);

        return new CommentPostDTO(commentPost);
    }

    public CommentPostDTO updateComment(Integer id_comment, String content) {
        Optional<Comment_Post> foundCommentPost = commentPostRepository.findById(id_comment);
        foundCommentPost.get().setContent(content);
        foundCommentPost.get().setEdit_time(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        commentPostRepository.save(foundCommentPost.get());
        return new CommentPostDTO(foundCommentPost.get());
    }

    public void deleteComment(int id) {
        commentPostRepository.deleteById(id);
    }

    public PostDTO getPostOfComment(Integer id) {
        Comment_Post commentPost = commentPostRepository.findById(id).get();
        if(commentPost.getPost() != null) return new PostDTO(commentPost.getPost());
        else {
            Comment_Post parent = commentPost.getParent();
            if(parent.getPost() != null) return new PostDTO(parent.getPost());
            else {
                Comment_Post firstParent = parent.getParent();
                return new PostDTO(firstParent.getPost());
            }
        }
    }
}
