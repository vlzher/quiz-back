package com.quiz.quizback.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizDTO {
    private Long id;
    private String title;
    private List<QuestionDTO> questions;
}
