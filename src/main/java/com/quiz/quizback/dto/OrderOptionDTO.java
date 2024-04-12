package com.quiz.quizback.dto;

import lombok.Data;

@Data
public class OrderOptionDTO {
    private Long id;
    private String text;
    private int correctOrder;
}
