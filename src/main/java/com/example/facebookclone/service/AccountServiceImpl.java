package com.example.facebookclone.service;

import com.example.facebookclone.DTO.UserProfileDTO;
import com.example.facebookclone.entity.Account;
import com.example.facebookclone.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findByAccountId(int accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.get();
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Page<Account> findByProfileName(String name, int limit) {
        return accountRepository.findByProfile_nameContaining(name, PageRequest.of(0, limit));
    }

    @Override
    public UserProfileDTO convertToUserProfileDTO(Account account) {
        int totalFriend = (int) (account.getFriends().stream().filter(friend -> friend.getAccept_time() != null).count() +
                        account.getFriendOf().stream().filter(friend -> friend.getAccept_time() != null).count());

        return new UserProfileDTO(account.getId(), account.getProfile_name(), account.getCome_from(), account.getLive_at(), account.getAvatar(),
                account.getCoverImage(), account.getDescription(), account.getBrithdate(), account.getCreate_time(),
                totalFriend);
    }
}
