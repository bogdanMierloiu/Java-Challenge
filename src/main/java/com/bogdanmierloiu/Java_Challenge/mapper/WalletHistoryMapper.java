package com.bogdanmierloiu.Java_Challenge.mapper;

import com.bogdanmierloiu.Java_Challenge.dto.wallet_history.WalletHistoryRequest;
import com.bogdanmierloiu.Java_Challenge.dto.wallet_history.WalletHistoryResponse;
import com.bogdanmierloiu.Java_Challenge.entity.WalletHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface WalletHistoryMapper {

    WalletHistory map(WalletHistoryRequest walletRequest);

    WalletHistoryResponse map(WalletHistory walletHistory);

    List<WalletHistoryResponse> map(List<WalletHistory> walletHistories);
}
