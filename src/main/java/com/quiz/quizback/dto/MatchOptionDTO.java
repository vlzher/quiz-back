package com.quiz.quizback.dto;

import lombok.Data;

@Data
public class MatchOptionDTO {
    private Long id;
    private String text;
    private boolean isLeft;
    private int correctAnswer;
}
