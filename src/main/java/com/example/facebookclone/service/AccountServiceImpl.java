package com.example.facebookclone.service;

import com.example.facebookclone.entity.Account;
import com.example.facebookclone.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
