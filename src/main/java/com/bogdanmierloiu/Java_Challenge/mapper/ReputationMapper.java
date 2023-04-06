package com.bogdanmierloiu.Java_Challenge.mapper;

import com.bogdanmierloiu.Java_Challenge.dto.reputation.ReputationRequest;
import com.bogdanmierloiu.Java_Challenge.dto.reputation.ReputationResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Reputation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ReputationMapper {

    Reputation map(ReputationRequest request);

    ReputationResponse map(Reputation reputation);

    List<ReputationResponse> map(List<Reputation> reputationList);
}
