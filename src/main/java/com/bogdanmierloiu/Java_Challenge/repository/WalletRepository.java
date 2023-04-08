package com.bogdanmierloiu.Java_Challenge.repository;

import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("SELECT w from Wallet w order by w.nrOfTokens desc ")
    List<Wallet> findAllOrderByTokens();
}
