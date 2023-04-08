package com.bogdanmierloiu.Java_Challenge.repository;

import com.bogdanmierloiu.Java_Challenge.entity.WalletHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletHistoryRepository extends JpaRepository<WalletHistory, Long> {

    List<WalletHistory> findByWalletIdOrderByDateTimeDesc(Long id);
}
