package com.bogdanmierloiu.Java_Challenge.dto.wallet;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Nft;
import lombok.Data;

import java.util.List;

@Data
public class WalletResponse {

    private Long id;

    private Long nrOfTokens;

    private String address;

    private List<Nft> nfts;

    private PlayerResponse player;
}
