package com.bogdanmierloiu.Java_Challenge.dto.wallet_history;

import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WalletHistoryResponse {

    private Long id;
    private LocalDateTime dateTime;
    public String event;
    private Long value;
    private WalletResponse wallet;
}
