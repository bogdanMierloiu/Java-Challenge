package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.nft.NftRequest;
import com.bogdanmierloiu.Java_Challenge.dto.nft.NftResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Nft;
import com.bogdanmierloiu.Java_Challenge.mapper.NftMapper;
import com.bogdanmierloiu.Java_Challenge.repository.NftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NftService implements CrudOperation<NftRequest, NftResponse> {

    private final NftRepository nftRepository;
    private final NftMapper nftMapper;

    public Nft createYoungExplorerNFT() {
        Nft nft = new Nft();
        nft.setType("Young Explorer");
        return nftRepository.save(nft);
    }

    public Nft createAdventurerNFT() {
        Nft nft = new Nft();
        nft.setType("Adventurer");
        return nftRepository.save(nft);
    }

    public Nft createCosmonautNFT() {
        Nft nft = new Nft();
        nft.setType("Cosmonaut");
        return nftRepository.save(nft);
    }

    public List<Nft> findByWallet(Long id) {
        return nftRepository.findByWalletId(id);
    }

    @Override
    public NftResponse add(NftRequest request) {
        return null;
    }

    @Override
    public List<NftResponse> getAll() {
        return null;
    }

    @Override
    public NftResponse findById(Long id) {
        return null;
    }

    @Override
    public NftResponse update(NftRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
