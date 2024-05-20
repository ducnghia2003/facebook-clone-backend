package com.example.facebookclone.service;

import com.example.facebookclone.DTO.PostDTO;
import com.example.facebookclone.DTO.ShareDTO;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Friend;
import com.example.facebookclone.entity.embeddedID.FriendId;
import com.example.facebookclone.repository.AccountRepository;
import com.example.facebookclone.repository.FriendRepository;
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
    @Autowired
    private FriendRepository friendRepository;

    public List<Object> getListPostAndShareOfPersonal(int id_account) {
        List<Object> listPostAndShare = getListPostAndShareOneAccountPersonal(id_account);
        return sortListPostAndShare(listPostAndShare);
    }
    public List<Object> getListPostAndShareOfFriends(int id_account) {
        Optional<Account> account = accountRepository.findById(id_account);
        List<Object> listPostAndShare = new ArrayList<>();
        List<Friend> friends = account.get().getFriends().stream().filter(friend -> friend.getAccept_time() != null).collect(Collectors.toList());
        List<Friend> friendOfs = account.get().getFriendOf().stream().filter(friend -> friend.getAccept_time() != null).collect(Collectors.toList());

        for(Friend friend : friends) {
            listPostAndShare.addAll(getListPostAndShareOneAccountFriend(friend.getReceiver().getId()));
        }

        for(Friend friend : friendOfs) {
            listPostAndShare.addAll(getListPostAndShareOneAccountFriend(friend.getSender().getId()));
        }

        return sortListPostAndShare(listPostAndShare);
    }
    public List<Object> getListPostAndShareOfOther(int id_account, int id_other) {
        boolean check = false;
        Friend friend_1 = friendRepository.findByFriendId(new FriendId(id_account, id_other));
        if(friend_1 != null && friend_1.getAccept_time() != null) check = true;
        Friend friend_2 = friendRepository.findByFriendId(new FriendId(id_other, id_account));
        if(friend_2 != null && friend_2.getAccept_time() != null) check = true;
        List<Object> listPostAndShare = new ArrayList<>();
        if(check) {
            listPostAndShare = getListPostAndShareOneAccountFriend(id_other);
        } else {
            listPostAndShare = getListPostAndShareOneAccountStranger(id_other);
        }
        return sortListPostAndShare(listPostAndShare);
    }
    public List<Object> getListPostAndShareOneAccountPersonal(int id_account) {
        Optional<Account> account = accountRepository.findById(id_account);
        List<PostDTO> postDTOS = account.get().getPosts().stream().map(PostDTO::new).collect(Collectors.toList());
        List<ShareDTO> shareDTOS = account.get().getShares().stream().map(ShareDTO::new).collect(Collectors.toList());
        List<Object> listPostAndShare = new ArrayList<>();
        listPostAndShare.addAll(postDTOS);
        listPostAndShare.addAll(shareDTOS);
        return listPostAndShare;
    }
    public List<Object> getListPostAndShareOneAccountFriend(int id_account) {
        Optional<Account> account = accountRepository.findById(id_account);
        List<PostDTO> postDTOS = account.get().getPosts().stream().filter(post -> !post.getView_mode().trim().equals("private")).map(PostDTO::new).collect(Collectors.toList());
        List<ShareDTO> shareDTOS = account.get().getShares().stream().filter(share -> !share.getView_mode().trim().equals("private")).map(ShareDTO::new).collect(Collectors.toList());
        List<Object> listPostAndShare = new ArrayList<>();
        listPostAndShare.addAll(postDTOS);
        listPostAndShare.addAll(shareDTOS);
        return listPostAndShare;
    }

    public List<Object> getListPostAndShareOneAccountStranger(int id_account) {
        Optional<Account> account = accountRepository.findById(id_account);
        List<PostDTO> postDTOS = account.get().getPosts().stream().filter(post -> post.getView_mode().trim().equals("public")).map(PostDTO::new).collect(Collectors.toList());
        List<ShareDTO> shareDTOS = account.get().getShares().stream().filter(share -> share.getView_mode().trim().equals("public")).map(ShareDTO::new).collect(Collectors.toList());
        List<Object> listPostAndShare = new ArrayList<>();
        listPostAndShare.addAll(postDTOS);
        listPostAndShare.addAll(shareDTOS);
        return listPostAndShare;
    }


    public List<Object> sortListPostAndShare(List<Object> listPostAndShare) {
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
