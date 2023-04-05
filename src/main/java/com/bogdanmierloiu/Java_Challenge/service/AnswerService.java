package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.answer.AnswerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.answer.AnswerResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Answer;
import com.bogdanmierloiu.Java_Challenge.mapper.AnswerMapper;
import com.bogdanmierloiu.Java_Challenge.repository.AnswerRepository;
import com.bogdanmierloiu.Java_Challenge.repository.PlayerRepository;
import com.bogdanmierloiu.Java_Challenge.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService implements CrudOperation<AnswerRequest, AnswerResponse> {

    private final AnswerRepository answerRepository;
    private final PlayerRepository playerRepository;

    private final QuestionRepository questionRepository;

    private final AnswerMapper answerMapper;


    @Override
    public AnswerResponse add(AnswerRequest request) {
        Answer answerToSave = answerMapper.map(request);
        answerToSave.setIsValid(false);
        answerToSave.setPlayer(playerRepository.findById(request.getPlayerId()).orElseThrow());
        answerToSave.setQuestion(questionRepository.findById(request.getQuestionId()).orElseThrow());
        return answerMapper.map(answerRepository.save(answerToSave));
    }

    public List<AnswerResponse> findByQuestion(Long id) {
        return answerMapper.map(answerRepository.findByQuestionId(id));
    }

    @Override
    public List<AnswerResponse> getAll() {

        return null;
    }

    @Override
    public AnswerResponse findById(Long id) {
        return null;
    }

    @Override
    public AnswerResponse update(AnswerRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
