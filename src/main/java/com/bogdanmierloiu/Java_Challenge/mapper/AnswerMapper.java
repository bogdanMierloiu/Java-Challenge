package com.bogdanmierloiu.Java_Challenge.mapper;

import com.bogdanmierloiu.Java_Challenge.dto.answer.AnswerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.answer.AnswerResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Answer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AnswerMapper {

    Answer map(AnswerRequest request);

    AnswerResponse map(Answer answer);

    List<AnswerResponse> map(List<Answer> answers);
}
