package com.example.facebookclone.repository;

import com.example.facebookclone.entity.Account;
import com.example.facebookclone.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Integer> {
    @Query(value = "SELECT * FROM notify WHERE from_account_id=?1 AND to_account_id=?2",nativeQuery = true)
    List<Notify> findByNotificationSenderAndNotificationReceiver(Integer from_account_id, Integer to_account_id);
}
