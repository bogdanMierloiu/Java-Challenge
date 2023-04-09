package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import com.bogdanmierloiu.Java_Challenge.mapper.PlayerMapper;
import com.bogdanmierloiu.Java_Challenge.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerService implements CrudOperation<PlayerRequest, PlayerResponse> {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final WalletService walletService;
    private final ReputationService reputationService;
    private final WalletHistoryService walletHistoryService;
    private final NftService nftService;

    @Override
    public PlayerResponse add(PlayerRequest request) {
        Player playerToSave = playerMapper.map(request);
        if (Objects.equals(playerToSave.getName(), "admin")) {
            Wallet adminWallet = walletService.createAdminWallet();
            playerToSave.setWallet(adminWallet);
            playerToSave.setReputation(reputationService.createCosmonautReputation());
            playerToSave.setIsBlocked(false);
            walletHistoryService.createBonusEvent("Admin tokens ", adminWallet);
        } else {
            Wallet wallet = walletService.createUserWallet();
            playerToSave.setWallet(wallet);
            playerToSave.setReputation(reputationService.createYoungExplorerReputation());
            playerToSave.getWallet().getNfts().add(nftService.createYoungExplorerNFT());
            playerToSave.setIsBlocked(false);
            walletHistoryService.createBonusEvent("Received: New player bonus", wallet);
        }
        return playerMapper.map(playerRepository.save(playerToSave));
    }

    public PlayerResponse findByName(String name) {
        return playerMapper.map(playerRepository.findByName(name));
    }

    public void blockPlayer(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow();
        player.setIsBlocked(true);
        playerRepository.save(player);
    }
    public void unblockPlayer(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow();
        player.setIsBlocked(false);
        playerRepository.save(player);
    }

    public PlayerResponse findByWalletId(Long walletId) {
        return playerMapper.map(playerRepository.findByWalletId(walletId));
    }

    public PlayerResponse findByWalletAddressOrPlayerName(String input) {
        Player player = playerRepository.findByWalletAddressOrPlayerName(input.trim());
        if (player == null) {
            throw new NotFoundException("Player not found !");
        }
        return playerMapper.map(player);
    }

    @Override
    public List<PlayerResponse> getAll() {
        return playerMapper.map(playerRepository.findAll());
    }

    public List<PlayerResponse> getAllByName() {
        return playerMapper.map(playerRepository.findAllOrderByName());
    }

    public List<PlayerResponse> findAllByOrderByTokens() {
        return playerMapper.map(playerRepository.findAllByOrderByWalletNrOfTokensDesc());
    }

    @Override
    public PlayerResponse findById(Long id) {
        return playerMapper.map(playerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("The player with id " + id + " not found")
        ));
    }

    public Player findByIdReturnPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("The player with id " + id + " not found")
        );
    }

    @Override
    public PlayerResponse update(PlayerRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
