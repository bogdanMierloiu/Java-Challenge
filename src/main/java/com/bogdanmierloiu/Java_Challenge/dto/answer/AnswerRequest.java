package com.bogdanmierloiu.Java_Challenge.dto.answer;

import lombok.Data;

@Data
public class AnswerRequest {

    private Long id;

    private String text;

    private Boolean isValid;

    private Long playerId;

    private Long questionId;
}
