package com.quiz.quizback.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizDTO {
    private String id;
    private String title;
    private List<QuestionDTO> questions;
    private String userID;
}
