package com.bogdanmierloiu.Java_Challenge.mapper;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletRequest;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface WalletMapper {

    Wallet map(WalletRequest request);

    WalletResponse map(Wallet wallet);

    List<WalletResponse> map(List<Wallet> wallets);

    @Named("mapWithoutPlayer")
    WalletResponse mapWithoutPlayer(Wallet wallet);

    default PlayerResponse map(Player player) {
        return player != null ? new PlayerResponse(player.getId(), player.getName()) : null;
    }
    @Named("mapWithPlayer")
    default WalletResponse mapWithPlayer(Wallet wallet) {
        WalletResponse walletResponse = mapWithoutPlayer(wallet);
        Player player = wallet.getPlayers().stream().findFirst().orElse(null);
        walletResponse.setPlayer(map(player));
        return walletResponse;
    }

    default List<WalletResponse> mapWithPlayerList(List<Wallet> wallets) {
        return wallets.stream()
                .map(wallet -> {
                    WalletResponse walletResponse = mapWithoutPlayer(wallet);
                    Player player = wallet.getPlayers().stream().findFirst().orElse(null);
                    walletResponse.setPlayer(map(player));
                    return walletResponse;
                })
                .collect(Collectors.toList());
    }
}
