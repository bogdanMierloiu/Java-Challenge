package com.bogdanmierloiu.Java_Challenge.dto.question;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionRequest {
    private Long id;

    private Long rewardTokens;

    private String text;

    private List<Long> playersIds = new ArrayList<>();
}
