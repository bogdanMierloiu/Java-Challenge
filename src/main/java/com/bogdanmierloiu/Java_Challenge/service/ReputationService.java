package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.reputation.ReputationRequest;
import com.bogdanmierloiu.Java_Challenge.dto.reputation.ReputationResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Nft;
import com.bogdanmierloiu.Java_Challenge.entity.Reputation;
import com.bogdanmierloiu.Java_Challenge.repository.ReputationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReputationService implements CrudOperation<ReputationRequest, ReputationResponse> {

    private final ReputationRepository reputationRepository;

    public Reputation createYoungExplorerReputation() {
        Reputation reputation = new Reputation();
        reputation.setType("Young Explorer");
        return reputationRepository.save(reputation);
    }

    public Reputation createAdventurerReputation() {
        Reputation reputation = new Reputation();
        reputation.setType("Adventurer");
        return reputationRepository.save(reputation);
    }

    public Reputation createCosmonautReputation() {
        Reputation reputation = new Reputation();
        reputation.setType("Cosmonaut");
        return reputationRepository.save(reputation);
    }

    @Override
    public ReputationResponse add(ReputationRequest request) {
        return null;
    }

    @Override
    public List<ReputationResponse> getAll() {
        return null;
    }

    @Override
    public ReputationResponse findById(Long id) {
        return null;
    }

    @Override
    public ReputationResponse update(ReputationRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
