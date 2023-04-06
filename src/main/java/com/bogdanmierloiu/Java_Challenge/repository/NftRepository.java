package com.bogdanmierloiu.Java_Challenge.repository;

import com.bogdanmierloiu.Java_Challenge.entity.Nft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NftRepository extends JpaRepository<Nft, Long> {
    @Query("SELECT n from Nft n JOIN n.wallets w WHERE w.id = :walletId")
    List<Nft> findByWalletId(@Param("walletId") Long walletId);

}
