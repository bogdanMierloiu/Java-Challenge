package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionRequest;
import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionResponse;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletRequest;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Question;
import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import com.bogdanmierloiu.Java_Challenge.exception.NotEnoughTokens;
import com.bogdanmierloiu.Java_Challenge.mapper.WalletMapper;
import com.bogdanmierloiu.Java_Challenge.repository.PlayerRepository;
import com.bogdanmierloiu.Java_Challenge.repository.QuestionRepository;
import com.bogdanmierloiu.Java_Challenge.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

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
    private final QuestionRepository questionRepository;
    private final PlayerRepository playerRepository;
    private final WalletHistoryService walletHistoryService;

    public static String generateWalletAddress() {
        final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final SecureRandom RANDOM = new SecureRandom();
        final StringBuilder sb = new StringBuilder("0x");
        for (int i = 0; i < 38; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

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

    public void sendTokens(Long senderWalletId, Long receiverWalletId, Long nrOfTokens) throws NotEnoughTokens {
        Wallet senderWallet = findWalletById(senderWalletId);
        Wallet receiverWallet = findWalletById(receiverWalletId);
        if (!checkTokensBeforeSend(senderWallet, nrOfTokens)) {
            throw new NotEnoughTokens("Not enough tokens available!");
        }
        senderWallet.setNrOfTokens(senderWallet.getNrOfTokens() - nrOfTokens);
        receiverWallet.setNrOfTokens(receiverWallet.getNrOfTokens() + nrOfTokens);
        walletRepository.saveAll(Arrays.asList(senderWallet, receiverWallet));
        //history
        walletHistoryService.createTokenTransferEvent(senderWallet, receiverWallet, nrOfTokens);
    }

    public Boolean checkTokensBeforeSend(Wallet senderWallet, Long nrOfTokensToTransfer) {
        List<Question> playerQuestions = questionRepository.findAllByPlayerIdAndIsResolvedFalse(senderWallet.getId());
        Long tokensReserved = 0L;
        for (var playerQuestion : playerQuestions) {
            tokensReserved += playerQuestion.getRewardTokens();
        }
        return senderWallet.getNrOfTokens() >= tokensReserved + nrOfTokensToTransfer;
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
        return walletMapper.map(walletRepository.findById(id).orElseThrow(
                () -> new NotFoundException("The wallet with " + id + " not found")
        ));
    }

    public Wallet findWalletById(Long id) {
        return walletRepository.findById(id).orElseThrow(
                () -> new NotFoundException("The wallet with " + id + " not found")
        );
    }

    @Override
    public WalletResponse update(WalletRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }


}
