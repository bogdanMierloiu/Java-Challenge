package com.bogdanmierloiu.Java_Challenge.mapper;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PlayerMapper {

    Player map(PlayerRequest request);

    PlayerResponse map(Player player);

    List<PlayerResponse> map(List<Player> players);
}
