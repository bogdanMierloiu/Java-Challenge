package com.bogdanmierloiu.Java_Challenge.repository;

import com.bogdanmierloiu.Java_Challenge.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByName(String name);

    List<Player> findAllByOrderByWalletNrOfTokensDesc();


    @Query("SELECT p FROM Player p ORDER BY p.name ")
    List<Player> findAllOrderByName();

    Player findByWalletId(Long id);
}
