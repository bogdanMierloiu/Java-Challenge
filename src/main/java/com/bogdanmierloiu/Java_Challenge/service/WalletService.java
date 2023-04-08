package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletRequest;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import com.bogdanmierloiu.Java_Challenge.mapper.WalletMapper;
import com.bogdanmierloiu.Java_Challenge.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletService implements CrudOperation<WalletRequest, WalletResponse> {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final NftService nftService;

    public Wallet createUserWallet() {
        Wallet wallet = new Wallet(100L, generateWalletAddress());
        return walletRepository.save(wallet);
    }

    public Wallet createAdminWallet() {
        Wallet wallet = new Wallet();
        wallet.setNrOfTokens(9_000_000L);
        wallet.setAddress(generateWalletAddress());
        wallet.getNfts().addAll(Arrays.asList(
                nftService.createYoungExplorerNFT(),
                nftService.createAdventurerNFT(),
                nftService.createCosmonautNFT()));
        return walletRepository.save(wallet);
    }

    @Override
    public WalletResponse add(WalletRequest request) {
        return null;
    }

    @Override
    public List<WalletResponse> getAll() {
        return walletMapper.mapWithPlayerList(walletRepository.findAllOrderByTokens());
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

    public static String generateWalletAddress() {
        final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final SecureRandom RANDOM = new SecureRandom();
        final StringBuilder sb = new StringBuilder("0x");
        for (int i = 0; i < 38; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }


}
