package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Reputation;
import com.bogdanmierloiu.Java_Challenge.entity.Wallet;
import com.bogdanmierloiu.Java_Challenge.mapper.PlayerMapper;
import com.bogdanmierloiu.Java_Challenge.repository.PlayerRepository;
import com.bogdanmierloiu.Java_Challenge.repository.ReputationRepository;
import com.bogdanmierloiu.Java_Challenge.repository.WalletRepository;
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
    private final WalletRepository walletRepository;
    private final ReputationRepository reputationRepository;

    private final PlayerMapper playerMapper;

    @Override
    public PlayerResponse add(PlayerRequest request) {
        Player playerToSave = playerMapper.map(request);
        Wallet wallet = new Wallet(100L);
        walletRepository.save(wallet);
        Reputation reputation = reputationRepository.findById(1L).orElseThrow();
        playerToSave.setWallet(wallet);
        playerToSave.setReputation(reputation);
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

    @Override
    public PlayerResponse update(PlayerRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
