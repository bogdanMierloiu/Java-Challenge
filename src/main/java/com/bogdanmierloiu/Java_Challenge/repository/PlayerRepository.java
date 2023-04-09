package com.bogdanmierloiu.Java_Challenge.repository;

import com.bogdanmierloiu.Java_Challenge.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByName(String name);

    List<Player> findAllByOrderByWalletNrOfTokensDesc();

    @Query("SELECT p FROM Player p JOIN FETCH p.wallet w where w.address LIKE %:walletAddress% ")
    Player findByWalletAddress(@Param("walletAddress") String walletAddress);


    @Query("SELECT p FROM Player p ORDER BY p.name ")
    List<Player> findAllOrderByName();

    Player findByWalletId(Long id);
}
