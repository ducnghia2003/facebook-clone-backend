package com.example.facebookclone.service;

import com.example.facebookclone.entity.Account;

public interface AccountService {
    Account findByAccountId(int accountId);
    Account findByUsername(String username);
}
