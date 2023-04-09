package com.bogdanmierloiu.Java_Challenge.repository;

import com.bogdanmierloiu.Java_Challenge.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long id);

    List<Answer> findAllByPlayerIdAndIsValid(Long playerId, boolean isValid);

    List<Answer> findAllByPlayerId(Long playerId);
}
