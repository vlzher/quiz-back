package com.quiz.quizback.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnswerMatchQuestionRequest {
    private List<List<String>> answer;
}
