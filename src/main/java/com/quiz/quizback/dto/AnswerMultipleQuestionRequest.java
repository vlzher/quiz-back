package com.quiz.quizback.dto;

import lombok.Data;

import java.util.List;
@Data
public class AnswerMultipleQuestionRequest {
    private List<String> answer;
}
