package com.bogdanmierloiu.Java_Challenge.repository;

import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
