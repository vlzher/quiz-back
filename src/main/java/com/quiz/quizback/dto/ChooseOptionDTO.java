package com.quiz.quizback.dto;

import lombok.Data;

@Data
public class ChooseOptionDTO {
    private Long id;
    private String text;
    private boolean isCorrect;
}
