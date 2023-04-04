package com.bogdanmierloiu.Java_Challenge.repository;

import com.bogdanmierloiu.Java_Challenge.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
