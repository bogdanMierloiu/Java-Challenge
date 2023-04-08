package com.bogdanmierloiu.Java_Challenge.dto.wallet;

import lombok.Data;

@Data
public class TransferTokens {

    private Long senderWalletId;
    private Long receiverWalletId;
    private Long nrOfTokens;
}
