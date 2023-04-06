package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.answer.AnswerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.answer.AnswerResponse;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Answer;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Question;
import com.bogdanmierloiu.Java_Challenge.mapper.AnswerMapper;
import com.bogdanmierloiu.Java_Challenge.repository.AnswerRepository;
import com.bogdanmierloiu.Java_Challenge.repository.PlayerRepository;
import com.bogdanmierloiu.Java_Challenge.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

    public void validateAnswer(Long answerId, Long playerId, Long questionId, Long playerWhoTryToValidateId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow();
        Player playerWhoAddAnswer = playerRepository.findById(playerId).orElseThrow();
        Question question = questionRepository.findById(questionId).orElseThrow();
        Player playerWhoPutTheQuestion = question.getPlayer();
        if (Objects.equals(playerWhoPutTheQuestion.getId(), playerWhoTryToValidateId)) {
            answer.setIsValid(true);
            question.setIsResolved(true);
            playerWhoAddAnswer.getWallet().setNrOfTokens(playerWhoAddAnswer.getWallet().getNrOfTokens() + question.getRewardTokens());
            playerWhoPutTheQuestion.getWallet().setNrOfTokens(playerWhoPutTheQuestion.getWallet().getNrOfTokens() - question.getRewardTokens());
            answerRepository.save(answer);
        }
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
