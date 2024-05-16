package com.example.facebookclone.service;

import com.example.facebookclone.DTO.PostDTO;
import com.example.facebookclone.DTO.ShareDTO;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Post;
import com.example.facebookclone.entity.Share;
import com.example.facebookclone.repository.AccountRepository;
import com.example.facebookclone.repository.PostRepository;
import com.example.facebookclone.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShareService {
    @Autowired
    private ShareRepository shareRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PostRepository postRepository;
    public List<ShareDTO> getSharesByAccountId(Integer accountId) {
        Optional<Account> account = accountRepository.findById(accountId);

        return account.get().getShares().stream().map(ShareDTO::new).collect(Collectors.toList());
    }

    public ShareDTO saveShare(Integer id_account, String content, String view_mode, Integer id_post) {
        Optional<Post> post = postRepository.findById(id_post);
        Share newShare = new Share(content, view_mode, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        newShare.setAccount(accountRepository.findById(id_account).get());
        newShare.setPost(post.get());
        return new ShareDTO(shareRepository.save(newShare));
    }

    public ShareDTO updateShare(Integer id_share, String content, String view_mode) {
        Optional<Share> foundShare = shareRepository.findById(id_share);
        if(foundShare.isPresent()) {
            if(content != null) {
                foundShare.get().setContent(content);
            }
            if(view_mode != null) {
                foundShare.get().setView_mode(view_mode);
            }
            foundShare.get().setEdit_time(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            shareRepository.save(foundShare.get());
            return new ShareDTO(foundShare.get());
        }
        return null;
    }

    @Transactional
    public void deleteShare(int id) {
        shareRepository.deleteById(id);
    }
}
