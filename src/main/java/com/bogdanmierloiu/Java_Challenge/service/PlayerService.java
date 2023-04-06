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
        Wallet wallet = walletService.createWallet();
        playerToSave.setWallet(wallet);
        playerToSave.setReputation(reputationService.createYoungExplorerReputation());
        playerToSave.getWallet().getNfts().add(nftService.createYoungExplorerNFT());
        walletHistoryService.createEvent("Received: New player bonus", wallet);
        return playerMapper.map(playerRepository.save(playerToSave));
    }

    public PlayerResponse findByName(String name) {
        return playerMapper.map(playerRepository.findByName(name));
    }

    @Override
    public List<PlayerResponse> getAll() {
        return playerMapper.map(playerRepository.findAll());
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
