package com.bogdanmierloiu.Java_Challenge.dto.question;

import lombok.Data;

@Data
public class QuestionRequest {
    private Long id;

    private Long rewardTokens;

    private String text;

    private Long playerId;
}
