package com.bogdanmierloiu.Java_Challenge.service;

import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionRequest;
import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Player;
import com.bogdanmierloiu.Java_Challenge.entity.Question;
import com.bogdanmierloiu.Java_Challenge.exception.NotEnoughTokens;
import com.bogdanmierloiu.Java_Challenge.mapper.QuestionMapper;
import com.bogdanmierloiu.Java_Challenge.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService implements CrudOperation<QuestionRequest, QuestionResponse> {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final PlayerService playerService;

    public void addFromPlayer(QuestionRequest questionRequest) throws NotEnoughTokens, DataIntegrityViolationException {
        Player playerWhoPutQuestion = playerService.findByIdReturnPlayer(questionRequest.getPlayerId());
        if (!checkTokensBeforeQuestion(playerWhoPutQuestion, questionRequest)) {
            throw new NotEnoughTokens("Not enough tokens available!");
        }
        if (questionRequest.getText().length() > 2000) {
            throw new DataIntegrityViolationException("Question is too long! Max : 2000 characters");
        }
        Question questionToSave = questionMapper.map(questionRequest);
        questionToSave.setIsResolved(false);
        questionToSave.setPlayer(playerWhoPutQuestion);
        questionMapper.map(questionRepository.save(questionToSave));
    }

    public Boolean checkTokensBeforeQuestion(Player player, QuestionRequest question) {
        List<QuestionResponse> playerQuestions = findAllByPlayerAndIsResolvedFalse(player.getId());
        Long tokensReserved = 0L;
        for (var playerQuestion : playerQuestions) {
            tokensReserved += playerQuestion.getRewardTokens();
        }
        return player.getWallet().getNrOfTokens() >= tokensReserved + question.getRewardTokens();
    }


    public List<QuestionResponse> findAllByPlayerAndIsResolvedFalse(Long playerId) {
        return questionMapper.map(questionRepository.findAllByPlayerIdAndIsResolvedFalse(playerId));
    }

    public List<QuestionResponse> findAllByPlayer(Long playerId) {
        return questionMapper.map(questionRepository.findAllByPlayerIdOrderByIsResolvedAsc(playerId));
    }

    @Override
    public QuestionResponse add(QuestionRequest request) {
        return null;
    }

    @Override
    public List<QuestionResponse> getAll() {
        return questionMapper.map(questionRepository.findAll());
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
        Question questionToUpdate = questionRepository.findById(request.getId()).orElseThrow(
                () -> new NotFoundException("Question not found !")
        );
        questionToUpdate.setText(request.getText() != null ? request.getText() : questionToUpdate.getText());
        questionToUpdate.setRewardTokens(request.getRewardTokens() != null ? request.getRewardTokens() : questionToUpdate.getRewardTokens());

        return questionMapper.map(questionRepository.save(questionToUpdate));
    }

    @Override
    public void delete(Long id) {
        Question questionToDelete = questionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Question not found !")
        );
        questionRepository.delete(questionToDelete);
    }
}
