package com.example.facebookclone.service;

import com.example.facebookclone.DTO.PostDTO;
import com.example.facebookclone.DTO.ShareDTO;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Friend;
import com.example.facebookclone.repository.AccountRepository;
import com.example.facebookclone.repository.PostRepository;
import com.example.facebookclone.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostShareService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ShareRepository shareRepository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Object> getListPostAndShareOfPersonal(int id_account) {
//        System.out.println(account.get().getFriends().size());
//        System.out.println(account.get().getFriendOf().size());
        List<Object> listPostAndShare = getListPostAndShareOneAccount(id_account);
        listPostAndShare.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                LocalDateTime dateTime1 = (o1.getClass().getSimpleName().equals("PostDTO")) ? ((PostDTO)o1).getCreate_time() : ((ShareDTO)o1).getCreate_time();
                LocalDateTime dateTime2 = (o2.getClass().getSimpleName().equals("PostDTO")) ? ((PostDTO)o2).getCreate_time() : ((ShareDTO)o2).getCreate_time();
                return -dateTime1.compareTo(dateTime2);
            }
        });
        return listPostAndShare;
    }

    public List<Object> getListPostAndShareOneAccount(int id_account) {
        Optional<Account> account = accountRepository.findById(id_account);
        List<PostDTO> postDTOS = account.get().getPosts().stream().map(PostDTO::new).collect(Collectors.toList());
        List<ShareDTO> shareDTOS = account.get().getShares().stream().map(ShareDTO::new).collect(Collectors.toList());
        List<Object> listPostAndShare = new ArrayList<>();
        listPostAndShare.addAll(postDTOS);
        listPostAndShare.addAll(shareDTOS);
        return listPostAndShare;
    }


    public List<Object> getListPostAndShareOfFriends(int id_account) {
        Optional<Account> account = accountRepository.findById(id_account);
        List<Object> listPostAndShare = new ArrayList<>();
        List<Friend> friends = account.get().getFriends().stream().filter(friend -> friend.getAccept_time() != null).collect(Collectors.toList());
        List<Friend> friendOfs = account.get().getFriendOf().stream().filter(friend -> friend.getAccept_time() != null).collect(Collectors.toList());

        for(Friend friend : friends) {
            listPostAndShare.addAll(getListPostAndShareOneAccount(friend.getReceiver().getId()));
        }

        for(Friend friend : friendOfs) {
            listPostAndShare.addAll(getListPostAndShareOneAccount(friend.getSender().getId()));
        }

        listPostAndShare.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                LocalDateTime dateTime1 = (o1.getClass().getSimpleName().equals("PostDTO")) ? ((PostDTO)o1).getCreate_time() : ((ShareDTO)o1).getCreate_time();
                LocalDateTime dateTime2 = (o2.getClass().getSimpleName().equals("PostDTO")) ? ((PostDTO)o2).getCreate_time() : ((ShareDTO)o2).getCreate_time();
                return -dateTime1.compareTo(dateTime2);
            }
        });
        return listPostAndShare;
    }

}
