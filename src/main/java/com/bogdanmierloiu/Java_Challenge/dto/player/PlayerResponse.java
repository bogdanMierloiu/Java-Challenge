package com.bogdanmierloiu.Java_Challenge.dto.player;

import com.bogdanmierloiu.Java_Challenge.dto.nft.NftResponse;
import com.bogdanmierloiu.Java_Challenge.dto.reputation.ReputationResponse;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletResponse;
import lombok.Data;

import java.util.List;

@Data
public class PlayerResponse {
    private Long id;
    private String name;
    private ReputationResponse reputation;
    private WalletResponse wallet;



    public PlayerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
