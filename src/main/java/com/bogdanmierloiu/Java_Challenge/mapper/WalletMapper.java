package com.bogdanmierloiu.Java_Challenge.mapper;

import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletRequest;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface WalletMapper {

    Wallet map(WalletRequest request);

    WalletResponse map(Wallet wallet);

    List<WalletResponse> map(List<Wallet> wallets);
}
