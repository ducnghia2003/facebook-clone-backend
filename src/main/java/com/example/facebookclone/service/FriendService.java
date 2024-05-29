package com.example.facebookclone.service;

import com.example.facebookclone.DTO.FriendRequestDTO;
import com.example.facebookclone.DTO.SearchUserDTO;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Friend;
import com.example.facebookclone.entity.embeddedID.FriendId;
import com.example.facebookclone.repository.AccountRepository;
import com.example.facebookclone.repository.FriendRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final AccountService accountService;

    public FriendService(FriendRepository friendRepository, AccountService accountService) {
        this.friendRepository = friendRepository;
        this.accountService = accountService;
    }

    public boolean isFriend(int userId, int friendId) {
        Friend friend = friendRepository.findByFriendId(new FriendId(userId, friendId));
        if (friend == null) {
            friend = friendRepository.findByFriendId(new FriendId(friendId, userId));
        }
        return friend != null && friend.getAccept_time() != null;
    }

    public void addFriend(int senderId, int receiverId) {
        Account sender = accountService.findByAccountId(senderId);
        Account receiver = accountService.findByAccountId(receiverId);

        Friend friend = new Friend();
        friend.setFriendId(new FriendId(senderId, receiverId));
        friend.setSender(sender);
        friend.setReceiver(receiver);
        friend.setRequest_time(LocalDateTime.now());

        friendRepository.save(friend);
    }

    public void acceptFriend(int senderId, int receiverId) {
        Friend friend = friendRepository.findByFriendId(new FriendId(senderId, receiverId));
        friend.setAccept_time(LocalDateTime.now());

        friendRepository.save(friend);
    }

    public List<Friend> getAllRequest(int receiverId) {
        List<Friend> friends = new ArrayList<>();
        for (Friend friend: friendRepository.findByReceiverId(receiverId)) {
            if (friend.getAccept_time() == null) {
                friends.add(friend);
            }
        }
        return friends;
    }

    public void rejectFriend(int senderId, int receiverId) {
        Friend friend = friendRepository.findByFriendId(new FriendId(senderId, receiverId));
        if (friend != null) {
            friendRepository.delete(friend);
        }
    }

    public void removeFriend(int userId, int friendId) {
        Friend friend = friendRepository.findByFriendId(new FriendId(userId, friendId));
        if (friend == null) {
            friend = friendRepository.findByFriendId(new FriendId(friendId, userId));
        }
        if (friend != null) {
            friendRepository.delete(friend);
        }
    }

    public String getProfileStatus(int userId, int friendId) {
        if (userId == friendId)
            return "PERSONAL";
        Friend friend = friendRepository.findByFriendId(new FriendId(userId, friendId));
        if (friend == null) {
            friend = friendRepository.findByFriendId(new FriendId(friendId, userId));
        }

        if (friend == null)
            return "STRANGER";
        else if (friend.getAccept_time() == null)
            if (friend.getFriendId().getSenderId() == userId)
                return "IN_REQUEST_SENDER";
            else
                return "IN_REQUEST_RECEIVER";
        else
            return "IS_FRIEND";
    }
    public SearchUserDTO convertToSearchUserDTO(Account account) {
        return new SearchUserDTO(account.getId(), account.getProfile_name(), account.getAvatar());
    }

    public List<SearchUserDTO> searchUser(String name, int userId) {
        List<Account> accounts = accountService.findByProfileName(name, 10).getContent();
        List<SearchUserDTO> searchUserDTOs = new ArrayList<>();
        for (Account account: accounts) {
            if (account.getId() != userId) {
                String status = getProfileStatus(userId, account.getId());
                searchUserDTOs.add(new SearchUserDTO(account.getId(), account.getProfile_name(), account.getAvatar(), status));
            }
        }
        return searchUserDTOs;
    }

    public FriendRequestDTO convertToFriendRequestDTO(Friend friend) {
        Account sender = accountService.findByAccountId(friend.getFriendId().getSenderId());
        String time = getTimeDifference(friend.getRequest_time());

        return new FriendRequestDTO(sender.getId(), sender.getProfile_name(), sender.getAvatar(), time);
    }

    public List<FriendRequestDTO> getAllRequestUser(int receiverId) {
        List<Friend> friends = getAllRequest(receiverId);

        List<FriendRequestDTO> friendRequestDTOs = new ArrayList<>();
        for (Friend friend: friends) {
            friendRequestDTOs.add(convertToFriendRequestDTO(friend));
        }
        return friendRequestDTOs;
    }

    public String getTimeDifference(LocalDateTime requestTime) {
        LocalDateTime now = LocalDateTime.now();

        long minutes = ChronoUnit.MINUTES.between(requestTime, now);
        long hours = ChronoUnit.HOURS.between(requestTime, now);
        long days = ChronoUnit.DAYS.between(requestTime, now);
        long weeks = ChronoUnit.WEEKS.between(requestTime, now);

        if (minutes < 60) {
            return minutes + "m";
        } else if (hours < 24) {
            return hours + "h";
        } else if (days < 7) {
            return days + "d";
        } else {
            return weeks + "w";
        }
    }
}
