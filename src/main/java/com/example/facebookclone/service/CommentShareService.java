package com.example.facebookclone.service;

import com.cloudinary.utils.ObjectUtils;
import com.example.facebookclone.DTO.CommentPostDTO;
import com.example.facebookclone.DTO.CommentShareDTO;
import com.example.facebookclone.entity.*;
import com.example.facebookclone.repository.AccountRepository;
import com.example.facebookclone.repository.CommentShareRepository;
import com.example.facebookclone.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentShareService {
    @Autowired
    private CommentShareRepository commentShareRepository;
    @Autowired
    private ShareRepository shareRepository;
    @Autowired
    private AccountRepository accountRepository;
    public List<CommentShareDTO> getCommentsByShare(int id) {
        Optional<Share> share = shareRepository.findById(id);
        return share.get().getComments().stream().map(CommentShareDTO::new).collect(Collectors.toList());
    }

    public CommentShareDTO createComment(Integer id_account, Integer account_tag, Integer id_share, String content, MultipartFile image, Integer to_comment_id) {
        Optional<Account> account = accountRepository.findById(id_account);
        Comment_Share commentShare = new Comment_Share(content, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        if(id_share != null)  {
            Optional<Share> share = shareRepository.findById(id_share);
            commentShare.setShare(share.get());
        }
        commentShare.setAccount(account.get());

        if(account_tag != null) {
            commentShare.setAccount_tag(accountRepository.findById(account_tag).get());
        }

        if(image != null) {
            try {
                String url = cloudinary.getInstance().uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).values().toArray()[3].toString();
                commentShare.setImage(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(to_comment_id != null) {
            Optional<Comment_Share> foundCommentShare = commentShareRepository.findById(to_comment_id);
            foundCommentShare.get().addAnswer(commentShare);
            commentShareRepository.save(foundCommentShare.get());
            for(Comment_Share comment_share : foundCommentShare.get().getAnswers()) {
                if(comment_share.getAccount() == commentShare.getAccount() && comment_share.getCreate_time() == commentShare.getCreate_time()) {
                    return new CommentShareDTO(comment_share);
                }
            }
        } else {
            commentShareRepository.save(commentShare);
        }


        return new CommentShareDTO(commentShare);
    }

    public CommentShareDTO updateComment(Integer id_comment, String content) {
        Optional<Comment_Share> foundCommentShare = commentShareRepository.findById(id_comment);
        foundCommentShare.get().setContent(content);
        foundCommentShare.get().setEdit_time(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        commentShareRepository.save(foundCommentShare.get());
        return new CommentShareDTO(foundCommentShare.get());
    }

    public void deleteComment(int id) {
        commentShareRepository.deleteById(id);
    }
}
