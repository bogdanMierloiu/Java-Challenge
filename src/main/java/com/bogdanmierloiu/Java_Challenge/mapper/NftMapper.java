package com.bogdanmierloiu.Java_Challenge.mapper;

import com.bogdanmierloiu.Java_Challenge.dto.nft.NftRequest;
import com.bogdanmierloiu.Java_Challenge.dto.nft.NftResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Nft;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface NftMapper {

    Nft map(NftRequest request);

    NftResponse map(Nft nft);

    List<NftResponse> map(List<Nft> nfts);
}
