package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.wallet_history.WalletHistoryRequest;
import com.bogdanmierloiu.Java_Challenge.dto.wallet_history.WalletHistoryResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Question;
import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import com.bogdanmierloiu.Java_Challenge.entity.WalletHistory;
import com.bogdanmierloiu.Java_Challenge.mapper.WalletHistoryMapper;
import com.bogdanmierloiu.Java_Challenge.repository.WalletHistoryRepository;
import com.bogdanmierloiu.Java_Challenge.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletHistoryService implements CrudOperation<WalletHistoryRequest, WalletHistoryResponse> {
    private final WalletRepository walletRepository;
    private final WalletHistoryRepository walletHistoryRepository;

    private final WalletHistoryMapper walletHistoryMapper;


    public void createBonusEvent(String event, Wallet wallet) {
        WalletHistory walletHistory = new WalletHistory();
        walletHistory.setDateTime(LocalDateTime.now());
        walletHistory.setEvent(event);
        walletHistory.setWallet(wallet);
        walletHistory.setValue(wallet.getNrOfTokens());
        walletHistoryRepository.save(walletHistory);
    }

    public void createTransferTokensEvent(String event, Player playerWhoPutQuestion, Player playerWhoAddAnswer, Question question) {
        WalletHistory walletHistory = new WalletHistory();
        walletHistory.setDateTime(LocalDateTime.now());
        walletHistory.setEvent(event + " " + playerWhoPutQuestion.getName());
        walletHistory.setWallet(playerWhoAddAnswer.getWallet());
        walletHistory.setValue(question.getRewardTokens());
        walletHistoryRepository.save(walletHistory);
    }

    @Override
    public WalletHistoryResponse add(WalletHistoryRequest request) {
        WalletHistory walletHistory = walletHistoryMapper.map(request);
        walletHistory.setDateTime(LocalDateTime.now());
        walletHistory.setWallet(walletRepository.findById(request.getWalletId()).orElseThrow());
        return walletHistoryMapper.map(walletHistoryRepository.save(walletHistory));
    }

    public List<WalletHistoryResponse> findByWallet(Long id) {
        return walletHistoryMapper.map(walletHistoryRepository.findByWalletIdOrderByDateTimeDesc(id));
    }

    @Override
    public List<WalletHistoryResponse> getAll() {
        return null;
    }

    @Override
    public WalletHistoryResponse findById(Long id) {
        return null;
    }

    @Override
    public WalletHistoryResponse update(WalletHistoryRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
