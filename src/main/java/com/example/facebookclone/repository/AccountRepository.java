package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
    @Query("SELECT a FROM Account a WHERE a.profile_name LIKE %?1%")
    Page<Account> findByProfile_nameContaining(String name, PageRequest pageRequest);
}
