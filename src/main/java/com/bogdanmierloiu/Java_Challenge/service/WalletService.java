package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletRequest;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import com.bogdanmierloiu.Java_Challenge.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletService implements CrudOperation<WalletRequest, WalletResponse> {

    private final WalletRepository walletRepository;

    public Wallet createWallet() {
        Wallet wallet = new Wallet(100L);
        return walletRepository.save(wallet);
    }

    @Override
    public WalletResponse add(WalletRequest request) {
        return null;
    }

    @Override
    public List<WalletResponse> getAll() {
        return null;
    }

    @Override
    public WalletResponse findById(Long id) {
        return null;
    }

    @Override
    public WalletResponse update(WalletRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }


}
