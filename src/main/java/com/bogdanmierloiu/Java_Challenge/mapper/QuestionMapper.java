package com.bogdanmierloiu.Java_Challenge.mapper;

import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionRequest;
import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {

    Question map(QuestionRequest request);

    QuestionResponse map(Question question);

    List<QuestionResponse> map(List<Question> questions);





}
