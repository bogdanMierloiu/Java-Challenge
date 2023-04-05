package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionRequest;
import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Question;
import com.bogdanmierloiu.Java_Challenge.mapper.QuestionMapper;
import com.bogdanmierloiu.Java_Challenge.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService implements CrudOperation<QuestionRequest, QuestionResponse> {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;

    @Override
    public QuestionResponse add(QuestionRequest request) {
        Question questionToSave = questionMapper.map(request);
        questionToSave.setIsResolved(false);
        List<Player> players = questionMapper.mapPlayerIds(request.getPlayersIds());
        questionToSave.getPlayers().addAll(players);
        return questionMapper.map(questionRepository.save(questionToSave));
    }

    @Override
    public List<QuestionResponse> getAll() {
        return questionMapper.map(questionRepository.findAll());
    }

    public List<QuestionResponse> getAllUnresolved() {
        return questionMapper.map(questionRepository.findAllByIsResolvedFalse());
    }

    @Override
    public QuestionResponse findById(Long id) {
        return questionMapper.map(questionRepository.findById(id).orElseThrow());
    }

    @Override
    public QuestionResponse update(QuestionRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
