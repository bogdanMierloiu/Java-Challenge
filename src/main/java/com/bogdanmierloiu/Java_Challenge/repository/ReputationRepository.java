package com.bogdanmierloiu.Java_Challenge.repository;

import com.bogdanmierloiu.Java_Challenge.entity.Reputation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReputationRepository extends JpaRepository<Reputation, Long> {
}
