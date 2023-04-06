package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionRequest;
import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Question;
import com.bogdanmierloiu.Java_Challenge.exception.NotEnoughTokens;
import com.bogdanmierloiu.Java_Challenge.mapper.QuestionMapper;
import com.bogdanmierloiu.Java_Challenge.repository.PlayerRepository;
import com.bogdanmierloiu.Java_Challenge.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService implements CrudOperation<QuestionRequest, QuestionResponse> {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final PlayerService playerService;

    public QuestionResponse addFromPlayer(QuestionRequest questionRequest) throws NotEnoughTokens {
        Player playerWhoPutQuestion = playerService.findByIdReturnPlayer(questionRequest.getPlayerId());
        if (!checkPlayerTokens(playerWhoPutQuestion, questionRequest)) {
            throw new NotEnoughTokens("Not enough tokens available!");
        }
        Question questionToSave = questionMapper.map(questionRequest);
        questionToSave.setIsResolved(false);
        questionToSave.setPlayer(playerWhoPutQuestion);
        return questionMapper.map(questionRepository.save(questionToSave));
    }

    public Boolean checkPlayerTokens(Player player, QuestionRequest question) {
        List<QuestionResponse> playerQuestions = findAllByPlayerAndIsResolvedFalse(player.getId());
        Long tokensReserved = 0L;
        for (var playerQuestion : playerQuestions) {
            tokensReserved += playerQuestion.getRewardTokens();
        }
        if (player.getWallet().getNrOfTokens() < tokensReserved + question.getRewardTokens()) {
            return false;
        }
        return true;
    }

    @Override
    public QuestionResponse add(QuestionRequest request) {
        return null;
    }

    @Override
    public List<QuestionResponse> getAll() {
        return questionMapper.map(questionRepository.findAll());
    }

    public List<QuestionResponse> findAllByPlayerAndIsResolvedFalse(Long playerId) {
        return questionMapper.map(questionRepository.findAllByPlayerIdAndIsResolvedFalse(playerId));
    }

    public List<QuestionResponse> findAllByIsResolvedFalse() {
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
