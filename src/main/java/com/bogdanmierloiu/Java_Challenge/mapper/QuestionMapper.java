package com.bogdanmierloiu.Java_Challenge.mapper;

import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionRequest;
import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Question;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface QuestionMapper {

    Question map(QuestionRequest request);

    QuestionResponse map(Question question);

    List<QuestionResponse> map(List<Question> questions);

    default List<Player> mapPlayerIds(List<Long> playerIds) {
        return playerIds.stream().map(Player::new).collect(Collectors.toList());
    }

}
