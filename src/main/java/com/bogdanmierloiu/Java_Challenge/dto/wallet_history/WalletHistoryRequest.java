package com.bogdanmierloiu.Java_Challenge.dto.wallet_history;

import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WalletHistoryRequest {

    private Long id;
    private LocalDateTime dateTime;

    public String event;
    private Long value;

    private Long walletId;
}
